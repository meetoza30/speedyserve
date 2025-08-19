
import mongoose, { Schema, model} from "mongoose";

const orderSchema = new Schema({
    userId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User",  
        required : true
    },
    canteenId: {
        type: mongoose.Schema.Types.ObjectId,   
        ref: "Canteen",  
        required: true
    },
    dishes: [{
        dish: { type: mongoose.Schema.Types.ObjectId, ref: "Dish" },
        quantity: { type: Number, required: true },
        price: { type: Number, required: true },
    }],
    totalPrice: {
        type: Number,
        required: true,
        default:0
    },
    status: {
        type: String,
        enum: ["in queue", "preparing", "ready for pickup", "completed", "cancelled"],
        default: "in queue"
    },
    orderTime: {
        type: Date,
        default: Date.now
    },
    timeSlot : {
        type : String
    },
    preptime : {
        type : Number
    }
}, { timestamps: true });

export const Order = model("Order", orderSchema);
