import { Router } from "express";
import {registerUser} from "../controllers/user.controllers.js";
import {loginUser} from "../controllers/user.controllers.js";
import { registerValidation } from "../middlewares/auth.validation.js";
import { loginValidation } from "../middlewares/auth.validation.js";


const userRouter = Router()

userRouter.route("/signUp").post(registerValidation,registerUser);
userRouter.route("/signin").post(loginValidation,loginUser);


export default userRouter;