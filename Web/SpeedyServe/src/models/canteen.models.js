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
        type: [{}],  
        
    },
   slots : [{
    time : String,
    currentOrders: Number
   }],
    openingTime: {
        type: String,  
        
    },
    closingTime: {
        type: String,  
        
    },
    maxDishPerSlot: {
        type: Number,
    }
}, { timestamps: true });

export const Canteen = mongoose.model("Canteen", canteenSchema);
