import { Router } from "express";
import { loginCanteen, registerCanteen } from "../controllers/canteen.controllers.js";
import { loginValidation, registerValidation } from "../middlewares/auth.validation.js";

const canteenRouter = Router();

canteenRouter.route('/registerCanteen').post(registerCanteen);
canteenRouter.route('/loginCanteen').post(loginCanteen);

canteenRouter.route('/getPendingOrders').get()
export default canteenRouter;