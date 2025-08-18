import mongoose from "mongoose";
import jwt from 'jsonwebtoken';
import 'dotenv/config';


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
    
    openingTime: {
        type: String,  
    },
    closingTime: {
        type: String,  
        
    },
    stoves : {type : Number}, 
   slots: [{ type: mongoose.Schema.Types.ObjectId, ref: "Slot" }]
}, { timestamps: true });

//rating, image, short description,  

canteenSchema.methods.getJWToken = async function(){
    const canteen = this;
    const token = await jwt.sign({_id : canteen._id}, process.env.SPEEDY_SERVE_KEY , {expiresIn: '576h'});
    return token;

}

export const Canteen = mongoose.model("Canteen", canteenSchema);
