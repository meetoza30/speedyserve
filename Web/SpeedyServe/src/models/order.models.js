
import mongoose, { Mongoose } from "mongoose";

const orderSchema = new mongoose.Schema({
    userId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User",  
        // required: true
    },
    canteenId: {
        type: mongoose.Schema.Types.ObjectId,   
        ref: "Canteen",  
        required: true
    },
    dishes: [
        {   foodId : {
            type : mongoose.Schema.Types.ObjectId
        },
            foodName : String, // Dish id
            quantity: { type: Number, required: true, min: 1 }, // Minimum 1 quantity
            price: { type: Number, required: true } ,
            estimatedServingTime: { type: Number},
            isInCart : Boolean
        }
    ],
    totalPrice: {
        type: Number,
        required: true,
        default:0
    },
    status: {
        type: String,
        enum: ["pending", "preparing", "completed", "cancelled"],
        default: "pending"
    },
    orderTime: {
        type: Date,
        default: Date.now
    },
    timeSlot : {
        type : String
    },
    preptime : {
        type : Date
    }
}, { timestamps: true });

export const Order = mongoose.model("Order", orderSchema);
