import express from "express";
import cors from "cors";
import cookieParser from "cookie-parser";
import connectDB from "./src/db/db.js";
import userRouter from "./src/routes/user.routes.js"
import foodRouter from "./src/routes/food.routes.js";

const app = express()
const port = 3000

app.use(express.json());   // Parse JSON requests
app.use(cors());           // Enable CORS
app.use(cookieParser()); 

connectDB();



app.use("/api",userRouter);
app.use("/api/food",foodRouter);
app.use("/images",express.static('uploads'))


app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})

