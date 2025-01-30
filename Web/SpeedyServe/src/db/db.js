import mongoose from "mongoose";

const connectDB = async()=>{

      try{
        await mongoose.connect("mongodb+srv://ozameet300905:FCTXVjjXwfenjsds@speedyserve.k7tt5.mongodb.net/");
        console.log("Database Connection Successfully!!");
      }
      catch(err){
        console.log(`Error : ${err}`);
      }
}

export default connectDB;