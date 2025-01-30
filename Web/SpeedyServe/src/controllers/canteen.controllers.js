import { Canteen } from '../models/canteen.models.js';
import foodModel from "../models/food.models.js";


 const getCanteens = async (req, res) => {
    try {
        const canteens = await Canteen.find({}, 'canteenName openingtime closingtime'); // Fetch canteenName, openingtime, and closingtime
        res.status(200).json(canteens);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Internal server error' });
    }
};




const getCanteensWithDishes = async (req, res) => {
    try {
        const { canteenId } = req.params;

        const canteen = await Canteen.findById(canteenId)
            .populate("listDish", "name description price image category")  // Get dish details
            .select("name openingTime closingTime listDish");

        if (!canteen) {
            return res.status(404).json({ message: "Canteen not found" });
        }

        res.status(200).json(canteen);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "Internal server error" });
    }
};
export {getCanteens,getCanteensWithDishes};