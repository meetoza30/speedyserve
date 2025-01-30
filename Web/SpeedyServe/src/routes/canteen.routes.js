import { Router } from "express";
import { getCanteensWithDishes, loginCanteen, registerCanteen } from "../controllers/canteen.controllers.js";
import { loginValidation, registerValidation } from "../middlewares/auth.validation.js";
import { getCanteens } from '../controllers/canteen.controllers.js';

const canteenRouter = Router();

canteenRouter.route('/registerCanteen').post(registerCanteen);
canteenRouter.route('/loginCanteen').post(loginCanteen);
canteenRouter.get('/names', getCanteens);
canteenRouter.get('/:canteenId/dishes',getCanteensWithDishes)

export default canteenRouter;
