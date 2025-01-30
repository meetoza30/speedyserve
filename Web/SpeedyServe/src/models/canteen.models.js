import mongoose from "mongoose";

const canteenSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
        trim: true
    },
    listDish: [{  
        type: mongoose.Schema.Types.ObjectId,  // Storing ObjectId references to foodModel
        ref: "food",  // Refers to the foodModel collection
        required: true
    }],
    timeSlots: {  
        type: Number,
        required: true
    },
    openingTime: {
        type: String,  
        required: true
    },
    closingTime: {
        type: String,  
        required: true
    },
    maxDish: {
        type: Number,  
        required: true
    }
}, { timestamps: true });

export const Canteen = mongoose.model("Canteen", canteenSchema);
