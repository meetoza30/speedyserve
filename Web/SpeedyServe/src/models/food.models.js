import mongoose from "mongoose";

const foodSchema = new mongoose.Schema({

    name : {
        type:String,
        required:true
    },
    description: {
        type:String,
        required:true
    },
    price: {
        type:Number,
        required:true
    },
    image: {
        type:String,
        
    },
    estimateTime :{
        type : Number
    },
    category : {
        type:String,
        // required:true
    }

},{timestamps:true})

const foodModel = mongoose.models.food || mongoose.model("food",foodSchema);

export default foodModel;