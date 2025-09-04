import { Canteen } from "../models/canteen.models.js";
import { Order } from "../models/order.models.js";
import mongoose from "mongoose";


const getPendingOrders = async (req,res)=>{
    try {
     const {canteenId} = req.params;
     const canteen = await Canteen.findById(canteenId)
     const pendingOrders = await Order.find({canteenId, status: { $in: ["pending", "preparing"] }}).populate('userId', "username")
 
     if(!pendingOrders) res.json({message:"No pending orders"});
 
     res.status(200).json({success: true, pendingOrders});
 }
 catch(err){res.error(err)}
     
 }
 
 const updateOrderStatus = async(req,res)=>{
     try{const {canteenId} = req.params;
     const {orderId, status} = req.body;
     const order = await Order.findOne({_id : orderId})
     order.status = status;
     await order.save();
     res.status(200).json(order);
 }
 catch(err){
     res.json(err)
 }
 }
 
 const placeOrder = async (req, res) => {
    try {
        const { dishes, timeSlot, canteenId, totalPrice, prepTime, userId} = req.body;

        const canteen = await Canteen.findById(canteenId);
        if (!canteen) {
            return res.status(404).json({ error: "Canteen not found" });
        }

        let totalPrepTime = 0;

        // Find the time slot in the canteen's available slots
        // const slot = canteen.slots.find(slot => slot.time === timeSlot);
        // if (!slot) {
        //     return res.status(400).json({ error: "Invalid time slot" });
        // }
        const slot = timeSlot

        // Calculate total preparation time
        // for (let dish of dishes) {
        //     totalPrepTime += dish.estimatedServingTime || 0; 
        // }

        // const maxDishPerSlot = 10; // Define the max dish capacity per slot (adjust as needed)
        // if (totalPrepTime > maxDishPerSlot * 8) {
        //     return res.status(400).json({ error: "Time slot is full, choose another slot" });
        // }

        // Increase current orders in the slot
        // slot.currentOrders += 1;

        // Create a new order
        const order = new Order({
            timeSlot,
            dishes,
            totalPrice,
            canteenId,
            userId,
            status: "in queue" // Default status when order is placed
        });

        await order.save();
        await canteen.save(); // Save updated canteen slot info

        res.status(200).json({ message: "Order placed successfully", order });

    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

const addItemInOrder = async (req, res) => {
    try {
        const { canteenId, userId, foodId, quantity } = req.body;

        const canteen = await Canteen.findById(canteenId);
        if (!canteen) {
            return res.status(404).json({ error: "Canteen not found" });
        }

        const item = canteen.listDish.find(food => food._id.toString() === foodId);
        if (!item) {
            return res.status(404).json({ error: "Food item not found" });
        }

        const itemTotalPrice = item.price * (quantity || 1);

        let order = await Order.findOne({ userId, canteenId, status: "pending" });

        if (!order) {
            order = new Order({
                userId,
                canteenId,
                dishes: [{ foodName : item.name, quantity: quantity || 1, price: item.price }],
                totalPrice: itemTotalPrice,
                status: "pending",
            });
        } else {
            order.dishes.push({ foodName : item.name, quantity: quantity || 1, price: item.price });
            order.totalPrice += itemTotalPrice;
        }

        await order.save();
        res.status(200).json({ order });

    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

const getStausOfOrder = async(req,res)=>{
    try{
        const {orderId} = req.body;
        if(!orderId){
            res.json({message : "Order doesnt exist"})
            return;
        }
        const order = await Order.findById(orderId);
        if(!order) res.json({message : "Order doesnt exist"});
        res.json({status : order.status})
    }
    catch(err){
        res.error(err)
    }
}

const getCurrentOrders = async (req, res) => {
    try {
        const { canteenId } = req.params;

        const currentOrders = await Order.find({
            canteenId: new mongoose.Types.ObjectId(canteenId),
            status: { $in: ["in queue", "preparing"] }
        })
        .populate({
            path: 'dishes.dish',
            select: 'name price'
        })
        .populate('userId', 'username')
        .sort({ createdAt: 1 })
        .limit(10);

        res.status(200).json({ success: true, data: currentOrders });
    } catch (error) {
        console.error("Current orders error:", error);
        res.status(500).json({ success: false, message: "Server error" });
    }
};

const getTodayOrderTimeline = async (req, res) => {
    try {
        const { canteenId } = req.params;
        
        const today = new Date();
        const startOfDay = new Date(today.getFullYear(), today.getMonth(), today.getDate());
        const endOfDay = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);

        const hourlyData = await Order.aggregate([
            {
                $match: {
                    canteenId: new mongoose.Types.ObjectId(canteenId),
                    createdAt: { $gte: startOfDay, $lt: endOfDay }
                }
            },
            {
                $group: {
                    _id: { $hour: "$createdAt" },
                    count: { $sum: 1 }
                }
            },
            { $sort: { _id: 1 } }
        ]);

        res.status(200).json({ success: true, data: hourlyData });
    } catch (error) {
        console.error("Timeline error:", error);
        res.status(500).json({ success: false, message: "Server error" });
    }
};


const getAllTodayOrders = async (req, res) => {
    try {
        const { canteenId } = req.params;
        
        const today = new Date();
        const startOfDay = new Date(today.getFullYear(), today.getMonth(), today.getDate());
        const endOfDay = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);

        const todayOrders = await Order.find({
            canteenId: new mongoose.Types.ObjectId(canteenId),
            createdAt: { $gte: startOfDay, $lt: endOfDay }
        })
        .populate({
            path: 'dishes.dish',
            select: 'name price image category'
        })
        .populate('userId', 'username emailId mobile')
        .sort({ createdAt: -1 });

        res.status(200).json({ success: true, data: todayOrders });
    } catch (error) {
        console.error("Today orders error:", error);
        res.status(500).json({ success: false, message: "Server error" });
    }
};


export {addItemInOrder, getPendingOrders, updateOrderStatus, placeOrder, getCurrentOrders, getTodayOrderTimeline, getAllTodayOrders}