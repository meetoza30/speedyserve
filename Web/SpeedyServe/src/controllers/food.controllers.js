import foodModel from "../models/food.models.js";
import { Canteen } from "../models/canteen.models.js";
import fs from "fs";

const addFood = async(req, res) => {
    let image_filename = `${req.file.filename}`;
    const { canteenId } = req.params;  // Get the canteen ID from params

    const food = new foodModel({
        name: req.body.name,
        description: req.body.description,
        price: req.body.price,
        category: req.body.category,
        image: image_filename
    });

    try {
        await food.save();
        const canteen = await Canteen.findById(canteenId);
        canteen.listDish.push(food);
        await canteen.save();
        res.json({ success: true, message: "Food Added", name: canteen.name, food:food });
    } catch (error) {
        console.log(error);
        res.json({ success: false, message: error });
    }
};
   
   const listFood = async(req,res)=>{
        try{
            const foods = await foodModel.find({})
            res.json({success:true,data:foods})
        }catch(error){
            console.log(error);
            res.json({success:false , message:"Error"}); 
        }
   }
    
// Remove food item
const removeFood = async(req,res)=>{
        try{
            const food = await foodModel.findById(req.body.id)
            // console.log(food)
            fs.unlink(`uploads/${food.image}`,()=>{})

            await foodModel.findByIdAndDelete(req.body.id);
            res.json({success:true,message:"Food Removed"})

        }catch(error){
            // console.log(error);
            res.json({success:false,message:"Error"})
        }
}

export {addFood,listFood,removeFood}



