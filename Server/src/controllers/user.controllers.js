import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import { User } from "../models/user.models.js"; 
import { Order } from "../models/order.models.js";

// Register User
const registerUser = async (req, res) => {
    try {
        const { username, email, password, mobile } = req.body;

        // Check for missing fields
        if (!username || !email || !password || !mobile) {
            return res.status(400).json({ message: "All fields are required" });
        }

        // Check if user already exists
        const existingUser = await User.findOne({ $or: [{ username }, { email }, { mobile }] });
        if (existingUser) {
            return res.status(400).json({ message: "Username, Email, or Mobile number already exists" });
        }

        // Hash password
        const hashedPassword = await bcrypt.hash(password, 10);

        // Create new user
        const newUser = new User({ username, email, password: hashedPassword, mobile });
        const token = await newUser.getJWToken();
        await newUser.save();
        res.cookie("token", token, {
            httpOnly: true,
            secure: process.env.NODE_ENV === "production",
            sameSite: process.env.NODE_ENV === "production" ? "None" : "Lax",
            path: "/", 
            maxAge: 24 * 60 * 60 * 1000, 
        })
        res.status(201).json({ success : true, message: "You've registered successfully", token : token });

    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "Internal server error" });
    }
};

// Login User
const loginUser = async (req, res) => {
    try {
        const { username, password } = req.body;

        // Validate inputs
        if (!username || !password) {
            return res.status(400).json({ message: "All fields are required" });
        }

        // Check if user exists
        const user = await User.findOne({ username });
        if (!user) {
            return res.status(400).json({ message: "Invalid credentials" });
        }

        // Validate password
        const isPasswordValid = await bcrypt.compare(password, user.password);
        if (!isPasswordValid) {
            return res.status(400).json({ message: "Invalid credentials" });
        }

        // Generate JWT Token
        const token = await user.getJWToken();
         res.cookie("token", token, {
            httpOnly: true,
            secure: process.env.NODE_ENV === "production",
            sameSite: process.env.NODE_ENV === "production" ? "None" : "Lax",
            path: "/", 
        })

        res.status(200).json({ success : true,  message: "Login successful", token : token });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "Internal server error" });
    }
};

const getUserOrderDetails = async(req,res)=>{
   try {
    const order = await Order.findById(req._id);
    res.json(order);
}
catch(err){
    res.error(err)
}
}

const getPastOrders = async (req,res)=>{
    const orders = await Order.find({userId : req._id});
    res.json({pastOrders : orders})
}

export { registerUser, loginUser };
