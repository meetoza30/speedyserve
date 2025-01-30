import bcrypt from "bcrypt";

import { Canteen } from "../models/canteen.models.js";
import { Order } from "../models/order.models.js";

const registerCanteen = async (req, res)=>{
    try{
        const {name, password, emailId, mobile} = req.body;

                if (!name || !emailId || !password || !mobile) {
                    return res.status(400).json({ message: "All fields are required" });
                }
                const existingCanteen = await Canteen.findOne({ $or: [{ name }, { emailId }, { mobile }] });
                if (existingCanteen) {
                    return res.status(400).json({ message: "Canteen already exists" });
                }

                const hashedPassword = await bcrypt.hash(password, 10);
                
                        
                        const newCanteen = new Canteen({ name, emailId, password: hashedPassword, mobile });
                        await newCanteen.save();
                        res.status(201).json({ message: "Canteen registered successfully", newCanteen});
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
                const isPasswordValid = await bcrypt.compare(password, user.password);
                if (!isPasswordValid) {
                    return res.status(400).json({ message: "Invalid credentials" });
                }

                res.status(200).json({ message: "Login successful", canteen});
    }
    catch(err){
        console.log(err)
    }
}


const getPendingOrders = async (req,res)=>{
   try {
    const {canteenId} = req.params;
    const canteen = await Canteen.findById(canteenId)
    const pendingOrders = await Order.find({canteenId, status:"pending"})

    if(!pendingOrders) res.json({message:"No pending orders"});

    res.status(200).json(pendingOrders);
}
catch(err){res.error(err)}
    
}

const acceptOrder = async(req,res)=>{
    try{const {canteenId} = req.params;
    const {orderId} = req.body;


    const order = await Order.findOne({_id : orderId, canteenId, status:"pending"})
    order.status = "preparing";
    await order.save();
    res.status(200).json(order);
}
catch(err){
    res.error(err)
}
}


export {registerCanteen, loginCanteen} ;