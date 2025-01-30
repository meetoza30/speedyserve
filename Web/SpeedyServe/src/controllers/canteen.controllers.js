import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import { Canteen } from "../models/canteen.models.js";
import foodModel from "../models/food.models.js";

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



 const getCanteens = async (req, res) => {
    try {
        const canteens = await Canteen.find({});

        
        res.status(200).json(canteens);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Internal server error' });
    }
};




const getCanteensWithDishes  = async (req, res) => {
    try {
        const { canteenId } = req.params;

        // Find the canteen by ID and populate the dishes (listDish) array
        const canteen = await Canteen.findById(canteenId)
            .populate("listDish", "name description price image category");  // Populate the listDish field with dish details

        if (!canteen) {
            return res.status(404).json({ success: false, message: "Canteen not found" });
        }

        // Send the populated dishes array as the response
        res.status(200).json({
            success: true,
            canteen: canteen.name,
            dishes: canteen.listDish
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ success: false, message: "Internal server error" });
    }
};







export {getCanteens,getCanteensWithDishes,registerCanteen, loginCanteen};