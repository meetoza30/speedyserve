import mongoose, { Schema, model } from "mongoose";

const dishSchema = new Schema({
    canteenId : {
        type : mongoose.Schema.Types.ObjectId,
        ref : "Canteen"
    },
    name : { type: String, required: true }, 
    price: { type: Number, required: true },
    image : {type : String},
    description : {type : String, required : true},
    serveTime : {type : Number, required : true},
    category : {type : String}

}, {timestamps : true});

//dish image, dish rating

export const Dish = model("Dish", dishSchema);