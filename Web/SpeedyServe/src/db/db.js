import mongoose from "mongoose";
import 'dotenv/config'

const connectDB = async()=>{

      try{
        await mongoose.connect(ProcessingInstruction.env.MONGO_URI);
        console.log("Database Connection Successfully!!");
      }
      catch(err){
        console.log(`Error : ${err}`);
      }
}

export default connectDB;