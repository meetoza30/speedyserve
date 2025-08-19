import { Schema, model } from "mongoose";

const slotSchema = new Schema({
    startTime : {type : Date}, // HH:MM
    endTime : {type : Date}, // HH : MM
    maxOrders : {type : Number}
}, {timestamps: true});

export const Slot = model("Slot", slotSchema);