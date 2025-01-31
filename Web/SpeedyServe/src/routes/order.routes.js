import express, { Router } from "express";
import { Order } from "../models/order.models.js";
import { addItemInOrder, getPendingOrders, updateOrderStatus, placeOrder } from "../controllers/orders.controller.js";

const orderRouter = Router();

orderRouter.post('/addItemInOrder', addItemInOrder);
orderRouter.get('/:canteenId/getPendingOrders',getPendingOrders);
orderRouter.post('/:canteenId/updateOrderStatus', updateOrderStatus)
orderRouter.post('/placeOrder', placeOrder)
// orderRouter.get()

export default orderRouter;