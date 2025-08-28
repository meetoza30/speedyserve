import { Canteen } from "../models/canteen.models.js";
import { Order } from "../models/order.models.js";


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
     const order = await Order.findOne({_id : orderId, canteenId, status: { $in: ["pending", "preparing"] }})
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
        const { dishes, timeSlot, canteenId, totalPrice, preptime, userId} = req.body;

        const canteen = await Canteen.findById(canteenId);
        if (!canteen) {
            return res.status(404).json({ error: "Canteen not found" });
        }

      

        // Find the time slot in the canteen's available slots
        // const slot = canteen.slots.find(slot => slot.time === timeSlot);
        // if (!slot) {
        //     return res.status(400).json({ error: "Invalid time slot" });
        // }
     

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
            userId,
            timeSlot,
            dishes,
            totalPrice,
            canteenId,
            status: "in queue",
            preptime // Default status when order is placed
        });

        await order.save();

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

export {addItemInOrder, getPendingOrders, updateOrderStatus, placeOrder}