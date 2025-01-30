import mongoose from "mongoose";

const orderSchema = new mongoose.Schema({
    userId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User",  
        required: true
    },
    canteenId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Canteen",  // Linking the order to a specific canteen
        required: true
    },
    dishes: [
        {
            name: { type: String, required: true }, // Dish name
            quantity: { type: Number, required: true, min: 1 }, // Minimum 1 quantity
            price: { type: Number, required: true } // Price per dish
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
    }
}, { timestamps: true });

export const Order = mongoose.model("Order", orderSchema);
