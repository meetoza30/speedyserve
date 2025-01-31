import { Router } from "express";
import { getCanteens, getCanteensWithDishes, loginCanteen, registerCanteen, updateCanteen } from "../controllers/canteen.controllers.js";
import { loginValidation, registerValidation } from "../middlewares/auth.validation.js";

const canteenRouter = Router();

canteenRouter.route('/registerCanteen').post(registerCanteen);
canteenRouter.route('/loginCanteen').post(loginCanteen);
canteenRouter.route('/getCanteenDishes').post(getCanteensWithDishes)
canteenRouter.route('/getCanteens').get(getCanteens)
canteenRouter.route('/updateCanteen').post(updateCanteen)
// canteenRouter.route('/getPendingOrders').get()
export default canteenRouter;