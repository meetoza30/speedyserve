import express from "express";
import {addFood,listFood,removeFood}from "../controllers/food.controllers.js";
import multer from "multer";


const foodRouter = express.Router();

// Image Storage Engine

const storage = multer.diskStorage({
    destination:"./src/uploads",
    filename:(req,file,cb)=>{
        return cb(null,`${Date.now()}${file.originalname}`)

    }
})

const upload = multer({storage:storage})

foodRouter.post("/add/:canteenId",upload.single("image"),addFood)
foodRouter.get("/list",listFood)
foodRouter.post("/delete",removeFood)


export default foodRouter;