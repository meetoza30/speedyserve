import mongoose from "mongoose";

const canteenSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
        trim: true
    },
    password : {
        type: String,
        required: true
    },
    mobile: {
        type: Number,
        required: true
    },
    emailId : {
        type: String,
        required:true
    },
    listDish: {  
        type: [{}],  // Array of dish names
        
    },
    timeSlots: {  
        type: Number,
        
    },
    openingTime: {
        type: String,  // Consider using Date or HH:MM format validation
        
    },
    closingTime: {
        type: String,  // Consider using Date or HH:MM format validation
        
    },
    maxDish: {
        type: Number,  // Changed to Number since it's a count
        
    }
}, { timestamps: true });

export const Canteen = mongoose.model("Canteen", canteenSchema);
