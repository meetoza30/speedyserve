import { Canteen } from "../models/canteen.models";
import { Order } from "../models/order.models";

const addItemInOrder =async (req, res)=>{
    try{
        
        const {canteenId, userId, item} = req.body;
        const order = await Order.findById(req._id);
        if(!order){
            const arr = [item]
            totalPrice = item.price;
            const order = {userId, canteenId, arr, totalPrice, status:"pending"}
            
        }
        else{
            order.dishes.push(item);
            let totalPrice = totalPrice+item.price;
            
        }
        await order.save();
        
    }
    catch(err){
        res.error(err)
    }
}