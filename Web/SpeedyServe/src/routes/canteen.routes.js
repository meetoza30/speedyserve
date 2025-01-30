import { Router } from "express";
import { loginCanteen, registerCanteen } from "../controllers/canteen.controllers";
import { loginValidation, registerValidation } from "../middlewares/auth.validation";

const canteenRouter = Router();

canteenRouter.route('/registerCanteen').post(registerValidation, registerCanteen);
canteenRouter.route('/loginCanteen').post(loginValidation, loginCanteen);

export default canteenRouter;