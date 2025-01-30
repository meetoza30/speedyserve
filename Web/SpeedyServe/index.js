import express from "express";
import cors from "cors";
import cookieParser from "cookie-parser";
import connectDB from "./src/db/db.js";
import userRouter from "./src/routes/user.routes.js"
import foodRouter from "./src/routes/food.routes.js";
import 'dotenv/config'
import canteenRouter from "./src/routes/canteen.routes.js";

const app = express()


app.use(express.json()); 
app.use(cors());           
app.use(cookieParser()); 

connectDB();



app.use("/api",userRouter);
app.use("/api/food",foodRouter);
app.use("/canteens", canteenRouter)
app.use("/images",express.static('uploads'))


app.listen(process.env.PORT || 5000, () => {
  console.log(`Example app listening on port ${process.env.PORT}`)
})

