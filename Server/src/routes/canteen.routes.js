import { Router } from "express";
import { getCanteens, getCanteensWithDishes, loginCanteen, registerCanteen, getCanteenProfile, updateCanteenProfile, addDish, updateDish, getSlots, removeDish, getDashboardOverview, getWeeklyRevenue } from "../controllers/canteen.controllers.js";
import { loginValidation, registerValidation } from "../middlewares/auth.validation.js";
import upload from "../utils/multer.js";
const canteenRouter = Router();

canteenRouter.route('/registerCanteen').post(registerCanteen);
canteenRouter.route('/loginCanteen').post(loginCanteen);
canteenRouter.route('/getCanteenDishes').post(getCanteensWithDishes)
canteenRouter.route('/profile/:canteenId').get(getCanteenProfile);
canteenRouter.route('/profile/:canteenId').patch(updateCanteenProfile);
canteenRouter.route('/getCanteens').get(getCanteens)
canteenRouter.route('/removeDish').post(removeDish)
canteenRouter.route('/getSlots').post(getSlots);
canteenRouter.route('/get-canteen-dashboard/:canteenId').get(getDashboardOverview);
canteenRouter.route('/get-weekly-revenue/:canteenId').get(getWeeklyRevenue);
canteenRouter.route('/addDish').post(upload.single('image'), addDish);
canteenRouter.route('/updateDish').post(upload.single('image'), updateDish);
// canteenRouter.route('/getPendingOrders').get()

export default canteenRouter;