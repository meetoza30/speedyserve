import { Schema, model } from "mongoose";

const slotSchema = new Schema({
    startTime : {type : String, required : true}, // HH:MM
    endTime : {type : String, required : true}, // HH : MM
    maxOrders : {type : Number, required : true}
}, {timestamps: true});

export const Slot = model("Slot", slotSchema);