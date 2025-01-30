import mongoose from "mongoose";

const connectDB = async()=>{

      try{
        await mongoose.connect("mongodb://localhost:27017/SpeedyServe");
        console.log("Database Connection Successfully!!");
      }
      catch(err){
        console.log(`Error : ${err}`);
      }
}

export default connectDB;