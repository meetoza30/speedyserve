import mongoose from "mongoose";

const canteenSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
        trim: true
    },
    listDish: {  
        type: [String],  // Array of dish names
        required: true
    },
    timeSlots: {  
        type: Number,
        required: true
    },
    openingTime: {
        type: String,  // Consider using Date or HH:MM format validation
        required: true
    },
    closingTime: {
        type: String,  // Consider using Date or HH:MM format validation
        required: true
    },
    maxDish: {
        type: Number,  // Changed to Number since it's a count
        required: true
    }
}, { timestamps: true });

export const Canteen = mongoose.model("Canteen", canteenSchema);
