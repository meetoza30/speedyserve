import mongoose from "mongoose";
import 'dotenv/config';
import jwt from 'jsonwebtoken';

const userSchema = new mongoose.Schema({

    username:{
        type:String,
        required:true
    },
    email:{
        type:String,
        required:true,
        unique:true
    },
    mobile: {
        type: Number,
        required: true,
        minlength: 10,
        maxlength: 10
    },
     password:{
        type:String,
        required:true
    }

},{timestamps:true})

userSchema.methods.getJWToken = async function(){
    const user = this;
    const token = await jwt.sign({_id : user._id}, process.env.SPEEDY_SERVE_KEY, {expiresIn: '576h'});

    return token;
}

export const User = mongoose.model("User",userSchema)