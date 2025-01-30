import { Router } from "express";
import {registerUser} from "../controllers/user.controllers.js";
import {loginUser} from "../controllers/user.controllers.js";
import { registerValidation } from "../middlewares/auth.validation.js";
import { loginValidation } from "../middlewares/auth.validation.js";
import { updateUserProfile } from "../controllers/user.controller.js";
import { authenticateUser } from "../middlewares/auth.validation.js";

const userRouter = Router()

userRouter.route("/register").post(registerValidation,registerUser);
userRouter.route("/login").post(loginValidation,loginUser);
router.put("/profile", authenticateUser, updateUserProfile);

export default userRouter;