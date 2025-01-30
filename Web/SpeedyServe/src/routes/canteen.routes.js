import express from 'express';
import { getCanteens } from '../controllers/canteen.controllers.js';

const canteenRouter = express.Router();

canteenRouter.get('/', getCanteens);

export default canteenRouter;
