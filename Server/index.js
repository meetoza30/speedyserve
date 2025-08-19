import express from "express";
import cors from "cors";
import cookieParser from "cookie-parser";
import connectDB from "./src/db/db.js";
import userRouter from "./src/routes/user.routes.js"
// import foodRouter from "./src/routes/food.routes.js";
import 'dotenv/config'
import canteenRouter from "./src/routes/canteen.routes.js";
import orderRouter from "./src/routes/order.routes.js";

const app = express()


app.use(express.json()); 
app.use(cors());           
app.use(cookieParser()); 

connectDB();



app.use("/api",userRouter);
// app.use("/api/food",foodRouter);
app.use("/api", canteenRouter)
app.use('/orders', orderRouter)
// app.use("/images",e)


app.listen(process.env.PORT || 5000, () => {
  console.log(`App listening on port ${process.env.PORT}`)
})

