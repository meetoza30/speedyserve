import React, { useEffect, useState } from 'react';
import './Orders.css';
import { toast } from 'react-toastify';
import axios from 'axios';
import { assets } from '../../assets/assets';

const Order = () => {
  const [orders, setOrders] = useState([]);

  // Fetch all pending orders
  const fetchAllOrders = async () => {
    try {
      const response = await axios.get('http://192.168.67.201:3000/orders/679ba66c5a75c4d106e38cb7/getPendingOrders');
      if (response.data?.success) {
        setOrders(response.data.pendingOrders || []);
      } else {
        toast.error("Error fetching orders");
      }
    } catch (error) {
      console.error("Error fetching orders:", error);
      toast.error("Something went wrong. Please try again.");
    }
  };

  // Handle status change
  const handleStatusChange = async (orderId, newStatus) => {
    try {
      const response = await axios.post('http://192.168.67.201:3000/orders/679ba66c5a75c4d106e38cb7/updateOrderStatus', {
        orderId,
        status: newStatus,
      });
      console.log(orderId)
      if (response.data?.success) {
        toast.success("Order status updated successfully!");
        fetchAllOrders(); // Refresh the orders after update
      } else {
        toast.success("Order status updated successfully");
      }
    } catch (error) {
      console.error("Error updating order status:", error);
      toast.error("Something went wrong.");
    }
  };

  useEffect(() => {
    fetchAllOrders();
  }, []);

  return (
    <div className='order-container'>
      <h3>Pending Orders</h3>
      <div className="order-list">
        {orders.length > 0 ? (
          orders.map((order) => (
            <div key={order._id} className='order-card'>
              <div className="order-header">
                <img src={assets.parcel_icon} alt="Order Icon" />
                <div>
                  <p className='order-user'><b>User:</b> {order.userId || "Unknown"}</p>
                  <p className='order-time'><b>Order Time:</b> {new Date(order.orderTime).toLocaleString()}</p>
                  <p className='order-slot'><b>Time Slot:</b> {order.timeslot || "Not Assigned"}</p>
                </div>
              </div>

              <div className="order-details">
                <p className='order-items'><b>Items:</b></p>
                <ul>
                  {order.dishes.map((dish, index) => (
                    <li key={index}>
                      {dish.foodName} x {dish.quantity}
                    </li>
                  ))}
                </ul>
              </div>

              <div className="order-footer">
                <p className='order-price'><b>Total Price:</b> ₹{order.totalPrice}</p>
                <select 
                  className='order-status-dropdown' 
                  value={order.status} // Default status from DB
                  onChange={(e) => handleStatusChange(order._id, e.target.value)}
                >
                  <option value="pending">Pending</option>
                  <option value="preparing">Preparing</option>
                  <option value="completed">Completed</option>
                  <option value="cancelled">Denied</option>
                </select>
              </div>
            </div>
          ))
        ) : (
          <p className='no-orders'>No pending orders found.</p>
        )}
      </div>
    </div>
  );
};

export default Order;
