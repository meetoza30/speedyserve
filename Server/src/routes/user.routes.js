import { Router } from "express";
import {registerUser} from "../controllers/user.controllers.js";
import {loginUser} from "../controllers/user.controllers.js";
import { authenticateUser, registerValidation } from "../middlewares/auth.validation.js";
import { loginValidation } from "../middlewares/auth.validation.js";


const userRouter = Router()

userRouter.route("/signup").post(registerUser);
userRouter.route("/login").post(loginUser);
userRouter.route("/check-auth").get(authenticateUser);


export default userRouter;