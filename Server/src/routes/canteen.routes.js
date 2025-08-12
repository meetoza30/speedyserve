import { Router } from "express";
import { getCanteens, getCanteensWithDishes, loginCanteen, registerCanteen, addDish, updateCanteen, removeDish } from "../controllers/canteen.controllers.js";
import { loginValidation, registerValidation } from "../middlewares/auth.validation.js";
import upload from "../utils/multer.js";
const canteenRouter = Router();

canteenRouter.route('/registerCanteen').post(registerCanteen);
canteenRouter.route('/loginCanteen').post(loginCanteen);
canteenRouter.route('/getCanteenDishes').get(getCanteensWithDishes)
canteenRouter.route('/getCanteens').get(getCanteens)
canteenRouter.route('/updateCanteen').post(updateCanteen)
canteenRouter.route('/removeDish').post(removeDish)
canteenRouter.route('/addDish').post(upload.single('image'), addDish);
// canteenRouter.route('/getPendingOrders').get()
export default canteenRouter;