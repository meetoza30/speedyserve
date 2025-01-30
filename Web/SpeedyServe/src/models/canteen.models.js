import mongoose from "mongoose";

const canteenSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
        trim: true
    },
    password: {
        type: String,
        required: true
    },
    listDish: [{
        type: mongoose.Schema.Types.ObjectId,  // Storing ObjectId references to foodModel
        ref: "food",  // Refers to the food collection
        required: true
    }], // ✅ Fixed misplaced closing bracket
    mobile: {
        type: Number,
        required: true
    },
    timeSlots: {  
        type: Number,
        // required: true
    },
    openingTime: {  
        type: Number,
        // required: true
    },
    closingTime: {  
        type: Number,
        // required: true
    },
    emailId: {
        type: String,
        // required: true
    }
}, { timestamps: true });  // ✅ Moved this to the correct place

export const Canteen = mongoose.model("Canteen", canteenSchema);
