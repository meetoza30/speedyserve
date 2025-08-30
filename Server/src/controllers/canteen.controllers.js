import bcrypt from "bcrypt";
import cloudinary from "../utils/cloudinary.js";
import { Canteen } from "../models/canteen.models.js";
import { Dish } from "../models/dish.model.js";
import mongoose from "mongoose";
import { Slot } from "../models/slot.model.js";
import {Order} from '../models/order.models.js'

const CANTEEN_SAFE_DATA = "name mobile emailId openingTime closingTime";

//handles the availability of stoves
class MinHeap {
  constructor() { this.h = []; }
  push(v){ this.h.push(v); this._up(this.h.length-1); }
  pop(){
    if(this.h.length===0) return undefined;
    const top = this.h[0];
    const last = this.h.pop();
    if(this.h.length){ this.h[0]=last; this._down(0); }
    return top;
  }
  _up(i){ while(i>0){ const p=(i-1>>1); if(this.h[p]<=this.h[i]) break; [this.h[p],this.h[i]]=[this.h[i],this.h[p]]; i=p; } }
  _down(i){ const n=this.h.length; for(;;){ let s=i, l=i*2+1, r=l+1;
    if(l<n && this.h[l]<this.h[s]) s=l;
    if(r<n && this.h[r]<this.h[s]) s=r;
    if(s===i) break;
    [this.h[i],this.h[s]]=[this.h[s],this.h[i]]; i=s;
  }}
  size(){ return this.h.length; }
}

//some util functns that will be used below in the main logic

const MS_PER_MIN = 60_000;

function toHHMM(date, timeZone = "Asia/Kolkata") {
  return date.toLocaleTimeString("en-IN", { hour12: false, hour: "2-digit", minute: "2-digit", timeZone });
}

function parseHHMMForDate(base, hhmm) {
  const [h, m] = hhmm.split(":").map(Number);
  const d = new Date(base);
  d.setHours(h, m, 0, 0);
  return d;
}

function addMinutes(date, mins) {
  return new Date(date.getTime() + mins * MS_PER_MIN);
}

function roundUpToInterval(date, intervalMinutes) {
  const t = date.getTime();
  const q = Math.ceil(t / (intervalMinutes * MS_PER_MIN));
  return new Date(q * intervalMinutes * MS_PER_MIN);
}

//some more utils

function resolveServiceWindow(openingHHMM, closingHHMM, now = new Date()) {
  let open = parseHHMMForDate(now, openingHHMM);
  let close = parseHHMMForDate(now, closingHHMM);
  if (close <= open) close = addMinutes(close, 24 * 60); // overnight

  if (now < open) {
    // window later today
    return { open, close };
  }
  if (now >= open && now < close) {
    // currently in window
    return { open, close };
  }
  // window is over; move to next day's window
  open = addMinutes(open, 24 * 60);
  close = addMinutes(close, 24 * 60);
  return { open, close };
}

// Generate 15-min slots between [start, close], aligned on 15-min grid
function generateSlots(start, close, intervalMinutes) {
  const alignedStart = roundUpToInterval(start, intervalMinutes);
  const slots = [];
  let cur = alignedStart;
  while (cur < close) {
    const end = addMinutes(cur, intervalMinutes);
    if (end <= close) {
      slots.push({ start: new Date(cur), end: new Date(end) });
    }
    cur = end;
  }
  return slots;
}

const getSlots = async (req, res) => {
  try {
    const { canteenId, orderedDishes } = req.body;
    if (!mongoose.Types.ObjectId.isValid(canteenId)) {
      return res.status(400).json({ error: "Invalid canteenId" });
    }
    if (!Array.isArray(orderedDishes) || orderedDishes.length === 0) {
      return res.status(400).json({ error: "orderedDishes is required" });
    }

    // Configs (could be stored per-canteen)
    const slotDurationMinutes = 15;
    const defaultBufferMinutes = 5;

    // 1) Fetch canteen
    const canteen = await Canteen.findById(canteenId).lean();
    if (!canteen) return res.status(404).json({ error: "Canteen not found" });

    const openingTime = canteen.openingTime; // "HH:mm"
    const closingTime = canteen.closingTime; // "HH:mm"
    const stoveCount = Number(canteen.stoves) || 1;
    const bufferMinutes = Number(canteen.bufferMinutes) || defaultBufferMinutes;
    const maxOrdersPerSlot = Number(canteen.maxOrdersPerSlot) || stoveCount; // sensible default

    if (!openingTime || !closingTime) {
      return res.status(400).json({ error: "Canteen openingTime/closingTime not set" });
    }

    // 2) Resolve today's/next window
    const now = new Date();
    const { open, close } = resolveServiceWindow(openingTime, closingTime, now);

    // Service can start at max(now, open)
    const serviceStart = now < open ? open : now;

    // 3) Fetch dishes & build list of units to schedule
    const dishIds = orderedDishes.map(d => d.dishId);
    const dishes = await Dish.find({ _id: { $in: dishIds } }).select("_id serveTime").lean();



    const units = [];
    for (const od of orderedDishes) {
      const dish = dishes.find(dd => dd._id.toString() === od.dishId);
      const serve = Number(dish.serveTime);
      if (!Number.isFinite(serve) || serve <= 0) {
        return res.status(400).json({ error: `Invalid serveTime for dish ${od.dishId}` });
      }
      const qty = Number(od.quantity) || 0;
      for (let i = 0; i < qty; i++) units.push(serve);
    }

    // 4) Simulate cooking using a min-heap of stove free-times
    const heap = new MinHeap();
    for (let i = 0; i < stoveCount; i++) heap.push(serviceStart.getTime());

    let maxFinish = serviceStart.getTime();
    for (const serveMin of units) {
      const earliestFree = heap.pop();                // earliest stove free time (ms)
      const finish = earliestFree + serveMin * MS_PER_MIN;
      if (finish > maxFinish) maxFinish = finish;
      // Add buffer before this stove is considered free again
      heap.push(finish + bufferMinutes * MS_PER_MIN);
    }

    const earliestFinish = new Date(maxFinish);
    const earliestPickupSlotStart = roundUpToInterval(earliestFinish, slotDurationMinutes);

    // If cooking finishes after closing, no slots
    if (earliestPickupSlotStart >= close) {
      return res.json({
        earliestPickup: {
          iso: earliestFinish.toISOString(),
          hhmm: toHHMM(earliestFinish)
        },
        earliestPickupSlot: null,
        slots: []
      });
    }

    // 5) Generate candidate slots from earliestPickupSlotStart to close
    const allSlots = generateSlots(earliestPickupSlotStart, close, slotDurationMinutes);

    // 6) Get already booked counts for these slots (by HH:mm) for *this window only*
    const slotKeys = allSlots.map(s => toHHMM(s.start)); // "HH:mm"
    const counts = await Order.aggregate([
      {
        $match: {
          canteenId: new mongoose.Types.ObjectId(canteenId),
          timeSlot: { $in: slotKeys },                // stored as "HH:mm"
          createdAt: { $gte: open, $lt: close }       // only today's window
        }
      },
      { $group: { _id: "$timeSlot", c: { $sum: 1 } } }
    ]);
    const bookedMap = new Map(counts.map(x => [x._id, x.c]));

    // 7) Filter by capacity
    const available = allSlots.filter(s => {
      const key = toHHMM(s.start);
      const used = bookedMap.get(key) || 0;
      return used < maxOrdersPerSlot;
    });

    // Format output
    const slots = available.map(s => ({
      startISO: s.start.toISOString(),
      endISO: s.end.toISOString(),
      startHHMM: toHHMM(s.start),
      endHHMM: toHHMM(s.end)
    }));

    return res.json({
      earliestPickup: {
        iso: earliestFinish.toISOString(),
        hhmm: toHHMM(earliestFinish)
      },
      earliestPickupSlot: {
        iso: earliestPickupSlotStart.toISOString(),
        hhmm: toHHMM(earliestPickupSlotStart)
      },
      slots
    });

  } catch (err) {
    console.error("getSlots error:", err);
    return res.status(500).json({ error: "Server error", details: err.message });
  }
};

const registerCanteen = async (req, res)=>{
    try{
        const {name, password, emailId, mobile} = req.body;
        console.log(req.body)

                if (!name || !emailId || !password || !mobile) {
                    return res.status(400).json({ message: "All fields are required" });
                }
                const existingCanteen = await Canteen.findOne({ $or: [{ name }, { emailId }, { mobile }] });
                if (existingCanteen) {
                    return res.status(400).json({ message: "Email or phone already exists" });
                }

                const hashedPassword = await bcrypt.hash(password, 10);
                
                        
                        const newCanteen = new Canteen({ name, emailId, password: hashedPassword, mobile });
                        const token = await newCanteen.getJWToken();
                        console.log(token)
                        const canteen = await newCanteen.save();
                        res.cookie("token", token, {
                            path : '/',
                            httpOnly: true,
                            secure: process.env.NODE_ENV === "production",
                            sameSite: process.env.NODE_ENV === "production" ? "None" : "Lax",
                        })
                        res.status(201).json({ message: "Canteen registered successfully", token : token, canteenId : canteen._id});
    }
    catch(err){
        console.log(err)
    }

}

const loginCanteen = async (req, res)=>{
    try{
        const {emailId, password} = req.body;

        if (!emailId || !password) {
                    return res.status(400).json({ message: "All fields are required" });
                }
        
                // Check if user exists
                const canteen = await Canteen.findOne({ emailId });
                if (!canteen) {
                    return res.status(400).json({ message: "Invalid credentials" });
                }
        
                // Validate password
                const isPasswordValid = await bcrypt.compare(password, canteen.password);
                if (!isPasswordValid) {
                    return res.status(400).json({ message: "Invalid credentials" });
                }

                const token = await canteen.getJWToken();
                res.cookie("token", token, {
                            path : '/',
                            httpOnly: true,
                            secure: process.env.NODE_ENV === "production",
                            sameSite: process.env.NODE_ENV === "production" ? "None" : "Lax",
                        })

                res.status(200).json({success:true, message: "Login successful", token});
    }
    catch(err){
        console.log(err)
    }
}

const getCanteens = async (req, res) => {
    try {
        const canteens = await Canteen.find({});
        res.status(200).json({success : true , data :canteens});
    } catch (error) {
        console.error(error);
        res.status(500).json({success: false, message: 'Internal server error' });
    }
};

const getCanteensWithDishes  = async (req, res) => {
    try {
        const { canteenId } = req.body;

        const canteen = await Canteen.findById(canteenId).select(CANTEEN_SAFE_DATA);
        const dishes = await Dish.find({canteenId : canteenId});

        if (!dishes || !canteen) {
            return res.status(404).json({ success: false, message: "An error occured, please try again later" });
        }

        res.status(200).json({
            success: true,
            dishes,
            canteen
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ success: false, message: "Internal server error" });
    }
};

const updateCanteen = async(req, res)=>{
   try {
    const {canteenId, openingTime, closingTime} = req.body;
    const canteen  = await Canteen.findById(canteenId);
    canteen.openingTime = openingTime;
    canteen.closingTime = closingTime
    await canteen.save();
    res.json({message : "Update successful"})
}
catch(err){
    res.json({error : err})
}
}


const addDish = async (req, res) => {
    const { canteenId, name, description, price, category, serveTime} = req.body;
    console.log( canteenId, name, description, price, category, serveTime)

    try {
        const image_url = req?.file?.path; 
        
        const dish = new Dish({
            canteenId,
            name,
            description,
            price,
            category,
            image: image_url, 
            serveTime
        })
         dish.save();

        res.json({
            success : true,
            message: "Dish Added",
        });

    } catch (error) {
        console.log(error);
        res.json({ success: false, message: error.message || "Failed to add dish" });
    }
};

const updateDish = async(req, res) => {
    try {
        console.log('=== UPDATE DISH REQUEST ===');
        console.log('Body:', req.body);
        console.log('File:', req.file);
        console.log('Headers:', req.headers);

        const { canteenId, dishId } = req.body;
        
        // Validate required fields
        if (!canteenId) {
            return res.status(400).json({
                success: false,
                message: "canteenId is required"
            });
        }
        
        if (!dishId) {
            return res.status(400).json({
                success: false,
                message: "dishId is required"
            });
        }

        let updateData = {};
        
        // Handle FormData request (with potential image upload)
        if (req.file) {
            console.log('Processing FormData request with image');
            updateData = {
                name: req.body.name,
                description: req.body.description,
                price: Number(req.body.price),
                category: req.body.category,
                serveTime: Number(req.body.serveTime),
                image: req.file.path
            };
        } 
        // Handle JSON request (without image)
        else if (req.body.data) {
            console.log('Processing JSON request without image');
            updateData = {
                name: req.body.data.name,
                description: req.body.data.description,
                price: Number(req.body.data.price),
                category: req.body.data.category,
                serveTime: Number(req.body.data.serveTime)
            };
        } 
        // Handle direct field access (fallback)
        else {
            console.log('Processing direct field request');
            updateData = {
                name: req.body.name,
                description: req.body.description,
                price: Number(req.body.price),
                category: req.body.category,
                serveTime: Number(req.body.serveTime)
            };
        }

        console.log('Update data prepared:', updateData);

        // Validate ObjectId format
        if (!mongoose.Types.ObjectId.isValid(dishId)) {
            return res.status(400).json({
                success: false,
                message: "Invalid dishId format"
            });
        }

        if (!mongoose.Types.ObjectId.isValid(canteenId)) {
            return res.status(400).json({
                success: false,
                message: "Invalid canteenId format"
            });
        }

        // Perform the update
        const dish = await Dish.findOneAndUpdate(
            { _id: dishId, canteenId },
            updateData,
            { new: true, runValidators: true }
        );

        if (!dish) {
            return res.status(404).json({
                success: false,
                message: "Dish not found or doesn't belong to this canteen"
            });
        }

        console.log('Dish updated successfully:', dish._id);

        res.json({
            success: true,
            message: "Dish Updated",
            dish
        });

    } catch(err) {
        console.error('=== UPDATE DISH ERROR ===');
        console.error('Error name:', err.name);
        console.error('Error message:', err.message);
        console.error('Error stack:', err.stack);
        
        res.status(500).json({
            success: false,
            message: `Database error: ${err.message}`,
            ...(process.env.NODE_ENV === 'development' && { 
                error: err.name,
                details: err.message 
            })
        });
    }
};


const extractPublicIdFromUrl = (url) => {
    const parts = url.split('/');
    const fileName = parts[parts.length - 1];
    return `dish_pics/${fileName.split('.')[0]}`;
};

const removeDish = async (req, res) => {
    try {
        const { canteenId, dishId } = req.body;

        const deletedDish  = await Dish.findOneAndDelete({canteenId, _id : dishId});
       
        const publicId = extractPublicIdFromUrl(deletedDish.image); 
        await cloudinary.uploader.destroy(publicId);

        res.json({ success: true, message: "Dish Removed" });
    } catch (error) {
        console.error(error);
        res.json({ success: false, message: "Error removing dish" });
    }};

// canteen dashboard routes
const getDashboardOverview = async (req, res) => {
   
    try {
       
      //  console.log(req.body.canteenId)
   console.log(req.params);
  //  console.log(req)

    const { canteenId } = req.params;
        
        // if (!mongoose.Types.ObjectId.isValid(canteenId)) {
        //     return res.status(400).json({ error: "Invalid canteenId" });
        // }

        const today = new Date();
        const startOfDay = new Date(today.getFullYear(), today.getMonth(), today.getDate());
        const endOfDay = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);

        // Get today's stats
        const [todayOrders, todayRevenue, activeOrders, popularDish] = await Promise.all([
            // Today's total orders
            Order.countDocuments({
                canteenId: new mongoose.Types.ObjectId(canteenId),
                createdAt: { $gte: startOfDay, $lt: endOfDay }
            }),

            // Today's revenue
            Order.aggregate([
                {
                    $match: {
                        canteenId: new mongoose.Types.ObjectId(canteenId),
                        createdAt: { $gte: startOfDay, $lt: endOfDay },
                        status: { $ne: "cancelled" }
                    }
                },
                { $group: { _id: null, total: { $sum: "$totalPrice" } } }
            ]),

            // Active orders (in queue + preparing)
            Order.countDocuments({
                canteenId: new mongoose.Types.ObjectId(canteenId),
                status: { $in: ["in queue", "preparing"] }
            }),

            // Most popular dish today
            Order.aggregate([
                {
                    $match: {
                        canteenId: new mongoose.Types.ObjectId(canteenId),
                        createdAt: { $gte: startOfDay, $lt: endOfDay },
                        status: { $ne: "cancelled" }
                    }
                },
                { $unwind: "$dishes" },
                {
                    $lookup: {
                        from: "dishes",
                        localField: "dishes.dish",
                        foreignField: "_id",
                        as: "dishInfo"
                    }
                },
                { $unwind: "$dishInfo" },
                {
                    $group: {
                        _id: "$dishes.dish",
                        name: { $first: "$dishInfo.name" },
                        count: { $sum: "$dishes.quantity" }
                    }
                },
                { $sort: { count: -1 } },
                { $limit: 1 }
            ])
        ]);

        const response = {
            todayOrders,
            todayRevenue: todayRevenue[0]?.total || 0,
            activeOrders,
            popularItem: popularDish[0] || { name: "No orders yet", count: 0 }
        };

        res.status(200).json({ success: true, data: response });
    } catch (error) {
        console.error("Dashboard overview error:", error);
        res.status(500).json({ success: false, message: "Server error" });
    }
};

const getWeeklyRevenue = async (req, res) => {
    try {
        const { canteenId } = req.params;
        
        const today = new Date();
        const sevenDaysAgo = new Date(today.getTime() - 7 * 24 * 60 * 60 * 1000);

        const weeklyData = await Order.aggregate([
            {
                $match: {
                    canteenId: new mongoose.Types.ObjectId(canteenId),
                    createdAt: { $gte: sevenDaysAgo },
                    status: { $ne: "cancelled" }
                }
            },
            {
                $group: {
                    _id: {
                        $dateToString: { format: "%Y-%m-%d", date: "$createdAt" }
                    },
                    revenue: { $sum: "$totalPrice" },
                    orders: { $sum: 1 }
                }
            },
            { $sort: { _id: 1 } }
        ]);

        res.status(200).json({ success: true, data: weeklyData });
    } catch (error) {
        console.error("Weekly revenue error:", error);
        res.status(500).json({ success: false, message: "Server error" });
    }
};


export {registerCanteen, loginCanteen, updateCanteen, getCanteens, getCanteensWithDishes, addDish, updateDish, removeDish, getSlots, getDashboardOverview, getWeeklyRevenue} ;