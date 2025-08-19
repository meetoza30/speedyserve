import bcrypt from "bcrypt";
import cloudinary from "../utils/cloudinary.js";
import { Canteen } from "../models/canteen.models.js";
import { Dish } from "../models/dish.model.js";
import mongoose from "mongoose";
import { Slot } from "../models/slot.model.js";
import {Order} from '../models/order.models.js'

const CANTEEN_SAFE_DATA = "name mobile emailId openingTime closingTime";


// Helper: MinHeap for stove times
class MinHeap {
    constructor() { this.heap = []; }
    push(val) { this.heap.push(val); this.bubbleUp(this.heap.length - 1); }
    pop() {
        if (this.heap.length === 1) return this.heap.pop();
        const top = this.heap[0];
        this.heap[0] = this.heap.pop();
        this.bubbleDown(0);
        return top;
    }
    bubbleUp(i) {
        while (i > 0) {
            let p = Math.floor((i - 1) / 2);
            if (this.heap[p] <= this.heap[i]) break;
            [this.heap[p], this.heap[i]] = [this.heap[i], this.heap[p]];
            i = p;
        }
    }
    bubbleDown(i) {
        const len = this.heap.length;
        while (true) {
            let left = 2 * i + 1, right = 2 * i + 2, smallest = i;
            if (left < len && this.heap[left] < this.heap[smallest]) smallest = left;
            if (right < len && this.heap[right] < this.heap[smallest]) smallest = right;
            if (smallest === i) break;
            [this.heap[i], this.heap[smallest]] = [this.heap[smallest], this.heap[i]];
            i = smallest;
        }
    }
}

// helper function: convert HH:mm string to Date (today's date)
function getTodayTime(timeStr) {
    const [hour, minute] = timeStr.split(":").map(Number);
    const d = new Date();
    d.setHours(hour, minute, 0, 0);
    return d;
}

function generateSlotTimes(startTime, closingTime, intervalMinutes = 15) {
  const slots = [];
  let current = new Date(startTime);

  while (current <= closingTime) {
    slots.push(new Date(current)); // push as Date object
    current = new Date(current.getTime() + intervalMinutes * 60 * 1000); // add interval
  }

  return slots;
}

async function getAvailableSlots(canteenId, orderedDishes, bufferMinutes, slotDurationMinutes, stoveCount, closingTime, openingTime) {
    // Fetch dish details for serve times
    const dishDetails = await Dish.find({ _id: { $in: orderedDishes.map(d => d.dishId) } })
        .select("serveTime");
        console.log("dishDetails : ", dishDetails);

    const dishesWithPrepTime = orderedDishes.map(d => {
        const dish = dishDetails.find(x => x._id.toString() === d.dishId.toString());
        return { serveTime: dish.serveTime, quantity: d.quantity };
    });
    const openingDate = getTodayTime(openingTime)
    const closingDate = getTodayTime(closingTime)

    console.log("dishesWithPrepTime : ", dishesWithPrepTime)

    // Initialize stoves with current time
    const now = Date.now();
    const stoves = new MinHeap();
    for (let i = 0; i < stoveCount; i++) stoves.push(now);

    let maxFinishTime = openingDate; 
    // const slotTimes = generateSlotTimes(openingDate, closingDate);
    // Assign dishes to earliest free stove
   // Assign dishes to earliest free stove
for (let { serveTime, quantity } of dishesWithPrepTime) {
  for (let q = 0; q < quantity; q++) {
    const earliestFree = stoves.pop(); // take earliest available stove time
    const finishTime = earliestFree + (serveTime * 60 * 1000); // add dish prep time
    maxFinishTime = Math.max(maxFinishTime, finishTime);
    console.log("maxFinishTime : ", new Date(maxFinishTime));
    stoves.push(finishTime + bufferMinutes * 60 * 1000); // put stove back with buffer
  }
}


    const earliestPickup = new Date(maxFinishTime);

    // Convert closingTime string → Date object
    // const [closeHour, closeMinute] = closingTime.split(":").map(Number);

 
    console.log("closing date : ", closingDate)
    // closingDate.setHours(closeHour, closeMinute, 0, 0);

    let slots = [];
    let current = earliestPickup.getTime();

    while (current + slotDurationMinutes * 60000 <= closingDate.getTime()) {
        const slotStart = new Date(current);
        const slotEnd = new Date(current + slotDurationMinutes * 60000);

        const existingSlot = await Slot.findOne({ 
            startTime: slotStart.toTimeString().slice(0,5)
        });

        const orderCount = await Order.countDocuments({ timeSlot: slotStart.toTimeString().slice(0,5) });

        if (!existingSlot || existingSlot.maxOrders > orderCount) {
            slots.push({ startTime: slotStart, endTime: slotEnd });
        }

        current += slotDurationMinutes * 60000;
    }

    return { earliestPickup, slots };
}

function addMinutes(date, minutes) {
  return new Date(date.getTime() + minutes * 60000);
}

// ---------------- Controller Logic ----------------
const generateAvailableSlots = async (req, res) => {
  try {
    const { canteenId, orderedDishes } = req.body;

    // Fetch canteen details (e.g., opening, closing, buffer, slot duration)
    const canteen = await Canteen.findById(canteenId);
    if (!canteen) return res.status(404).json({ message: "Canteen not found" });

    const { openingTime, closingTime, bufferMinutes = 5, slotDurationMinutes = 15 } = canteen;

    // Fetch dish details
    const dishIds = orderedDishes.map(d => d.dishId);
    const dishDetails = await Dish.find({ _id: { $in: dishIds } }, { serveTime: 1 });

    // Map serveTime × quantity
    const dishesWithPrepTime = orderedDishes.map(order => {
      const dish = dishDetails.find(d => d._id.toString() === order.dishId);
      return { serveTime: dish.serveTime, quantity: order.quantity };
    });

    // Calculate total required preparation time
    const maxFinishTime = dishesWithPrepTime.reduce(
      (acc, d) => acc + (d.serveTime * d.quantity),
      0
    );

    // Earliest pickup time = now + total prep time + buffer
    const now = new Date();
    const earliestPickup = addMinutes(now, maxFinishTime + bufferMinutes);

    // Generate all slots between opening & closing
    let slotStart = new Date(openingTime);
    let slotEnd = addMinutes(slotStart, slotDurationMinutes);
    const slots = [];

    while (slotEnd <= new Date(closingTime)) {
      slots.push({
        canteenId,
        startTime: slotStart,
        endTime: slotEnd,
        maxOrders: canteen.maxOrdersPerSlot || 10 // default fallback
      });
      slotStart = slotEnd;
      slotEnd = addMinutes(slotStart, slotDurationMinutes);
    }

    // Optional: Store slots in DB (if you want to persist them)
    // await Slot.insertMany(slots);

    const earliestPickupFormatted = new Date(earliestPickup).toLocaleTimeString("en-IN", {
  hour: "2-digit",
  minute: "2-digit",
  hour12: false
});

    res.json({
      earliestPickupFormatted,
      availableSlots: slots
    });

  } catch (err) {
    console.error(err);
    res.status(500).json({ message: "Server error", error: err.message });
  }
};


// Controller
const getSlots = async (req, res) => {
    try {
        const { canteenId, orderedDishes } = req.body;
        console.log("orderedDishes : ", orderedDishes);
        const slotDurationMinutes = 15, bufferMinutes = 5;

        const canteen = await Canteen.findById(canteenId);
        if (!canteen) return res.status(404).json({ error: "Canteen not found" });

        const { stoveCount, closingTime, openingTime } = canteen; 
        console.log("closing time : ", closingTime);

        const { earliestPickup, slots } = await getAvailableSlots(
            canteenId,
            orderedDishes,
            bufferMinutes,
            slotDurationMinutes,
            stoveCount,
            closingTime,
            openingTime
        );

        res.json({ earliestPickup, slots });
    } catch (err) {
        console.error(err);
        res.status(500).json({ error: "Server error" });
    }
};




const registerCanteen = async (req, res)=>{
    try{
        const {name, password, emailId, mobile} = req.body;

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
                        await newCanteen.save();
                        res.cookie("token", token, {
                            path : '/',
                            httpOnly: true,
                            secure: process.env.NODE_ENV === "production",
                            sameSite: process.env.NODE_ENV === "production" ? "None" : "Lax",
                        })
                        res.status(201).json({ message: "Canteen registered successfully", token : token});
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


export {registerCanteen, loginCanteen, updateCanteen, getCanteens, getCanteensWithDishes, generateAvailableSlots, addDish, removeDish, getSlots} ;