import bcrypt from "bcrypt";
import cloudinary from "../utils/cloudinary.js";
import { Canteen } from "../models/canteen.models.js";
import { Dish } from "../models/dish.model.js";

const CANTEEN_SAFE_DATA = "name mobile emailId openingTime closingTime";

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


export {registerCanteen, loginCanteen, updateCanteen, getCanteens, getCanteensWithDishes, addDish, removeDish} ;