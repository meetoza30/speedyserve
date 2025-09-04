// src/components/OrdersManagement.js
import React, { useState, useEffect } from 'react';
import { 
    ClockIcon, 
    CheckCircleIcon, 
    XCircleIcon,
    EyeIcon,
    XMarkIcon,
    UserIcon,
    CurrencyRupeeIcon,
    CalendarDaysIcon,
    TagIcon
} from '@heroicons/react/24/outline';

const Orders = () => {
    const [activeTab, setActiveTab] = useState('current'); 
    const [currentOrders, setCurrentOrders] = useState([]);
    const [todayOrders, setTodayOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedOrder, setSelectedOrder] = useState(null);
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [updating, setUpdating] = useState({});

    const canteenId = localStorage.getItem('canteenId') || '689b8131d6a5e14a15e8e0bf';

    useEffect(() => {
        fetchOrders();
        
        // Auto-refresh current orders every 30 seconds
        const interval = setInterval(() => {
            if (activeTab === 'current') {
                fetchCurrentOrders();
            }
        }, 30000);

        return () => clearInterval(interval);
    }, [canteenId, activeTab]);

    const fetchOrders = async () => {
        setLoading(true);
        try {
            await Promise.all([fetchCurrentOrders(), fetchTodayOrders()]);
        } catch (error) {
            console.error('Error fetching orders:', error);
        } finally {
            setLoading(false);
        }
    };

    const fetchCurrentOrders = async () => {
        try {
            const response = await fetch(`http://localhost:3000/orders/get-current-orders/${canteenId}`);
            const result = await response.json();
            if (result.success) {
                setCurrentOrders(result.data || []);
            }
        } catch (error) {
            console.error('Error fetching current orders:', error);
        }
    };

    const fetchTodayOrders = async () => {
        try {
            const response = await fetch(`http://localhost:3000/orders/get-today-orders/${canteenId}`);
            const result = await response.json();
            if (result.success) {
                setTodayOrders(result.data || []);
            }
        } catch (error) {
            console.error('Error fetching today orders:', error);
        }
    };

    const updateOrderStatus = async (orderId, newStatus) => {
        setUpdating(prev => ({ ...prev, [orderId]: true }));
        
        try {
            const response = await fetch(`http://localhost:3000/orders/${canteenId}/updateOrderStatus`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ orderId, status: newStatus })
            });

            if (response.ok) {
                await fetchOrders(); // Refresh both lists
                if (selectedOrder && selectedOrder._id === orderId) {
                    setSelectedOrder(prev => ({ ...prev, status: newStatus }));
                }
            }
        } catch (error) {
            console.error('Error updating order status:', error);
        } finally {
            setUpdating(prev => ({ ...prev, [orderId]: false }));
        }
    };

    const getStatusColor = (status) => {
        switch (status) {
            case 'in queue': return 'bg-yellow-100 text-yellow-800 border-yellow-200';
            case 'preparing': return 'bg-blue-100 text-blue-800 border-blue-200';
            case 'ready for pickup': return 'bg-green-100 text-green-800 border-green-200';
            case 'completed': return 'bg-gray-100 text-gray-800 border-gray-200';
            case 'cancelled': return 'bg-red-100 text-red-800 border-red-200';
            default: return 'bg-gray-100 text-gray-800 border-gray-200';
        }
    };

    const getStatusIcon = (status) => {
        switch (status) {
            case 'in queue': return <ClockIcon className="h-4 w-4" />;
            case 'preparing': return <ClockIcon className="h-4 w-4 animate-spin" />;
            case 'ready for pickup': return <CheckCircleIcon className="h-4 w-4" />;
            case 'completed': return <CheckCircleIcon className="h-4 w-4" />;
            case 'cancelled': return <XCircleIcon className="h-4 w-4" />;
            default: return <ClockIcon className="h-4 w-4" />;
        }
    };

    const formatTime = (dateString) => {
        return new Date(dateString).toLocaleTimeString('en-IN', {
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    const formatDate = (dateString) => {
        return new Date(dateString).toLocaleDateString('en-IN', {
            day: '2-digit',
            month: 'short',
            year: 'numeric'
        });
    };

    const openOrderDetails = (order) => {
        setSelectedOrder(order);
        setIsDialogOpen(true);
    };

    const closeOrderDetails = () => {
        setSelectedOrder(null);
        setIsDialogOpen(false);
    };

    const renderOrderCard = (order) => (
        <div key={order._id} className="bg-white rounded-lg border border-gray-200 p-4 hover:shadow-md transition-shadow">
            <div className="flex justify-between items-start mb-3">
                <div className="flex items-center space-x-3">
                    <div className="bg-orange-50 border border-orange-200 rounded-lg p-2">
                        <span className="text-sm font-bold text-orange-600">
                            #{order._id.slice(-6).toUpperCase()}
                        </span>
                    </div>
                    <div>
                        <p className="font-semibold text-gray-900">
                            {order.userId?.username || 'Anonymous'}
                        </p>
                        <p className="text-sm text-gray-500">
                            {formatTime(order.createdAt)} • Slot: {order.timeSlot || 'No slot'}
                        </p>
                    </div>
                </div>
                <span className={`px-3 py-1 rounded-full text-xs font-medium border flex items-center space-x-1 ${getStatusColor(order.status)}`}>
                    {getStatusIcon(order.status)}
                    <span className="capitalize">{order.status}</span>
                </span>
            </div>

            <div className="mb-4">
                <p className="text-sm text-gray-600 mb-2">Items ({order.dishes?.length || 0}):</p>
                <div className="text-sm text-gray-700">
                    {order.dishes?.slice(0, 2).map((dish, index) => (
                        <span key={index}>
                            {dish.dish?.name || 'Unknown Item'} ×{dish.quantity}
                            {index < 1 && order.dishes.length > 1 ? ', ' : ''}
                        </span>
                    ))}
                    {order.dishes?.length > 2 && (
                        <span className="text-gray-500"> +{order.dishes.length - 2} more</span>
                    )}
                </div>
            </div>

            <div className="flex justify-between items-center">
                <span className="text-lg font-bold text-gray-900">₹{order.totalPrice}</span>
                <div className="flex space-x-2">
                    <button 
                        onClick={() => openOrderDetails(order)}
                        className="flex items-center space-x-1 px-3 py-1 text-sm border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors"
                    >
                        <EyeIcon className="h-3 w-3" />
                        <span>Details</span>
                    </button>

                    {/* Status Update Buttons */}
                    <select
    value={order.status}
    onChange={(e) => updateOrderStatus(order._id, e.target.value)}
    disabled={updating[order._id]}
    className="border border-gray-300 rounded-lg px-3 py-2 text-sm focus:ring-orange-500 focus:border-orange-500 disabled:opacity-50 disabled:cursor-not-allowed"
>
    <option value="in queue">In Queue</option>
    <option value="preparing">Preparing</option>
    <option value="ready for pickup">Ready for Pickup</option>
    <option value="completed">Completed</option>
    <option value="cancelled">Cancelled</option>
</select>

                </div>
            </div>
        </div>
    );

    const activeOrders = activeTab === 'current' ? currentOrders : todayOrders;

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-50 flex items-center justify-center">
                <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-orange-500"></div>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-gray-50">
            {/* Header */}
            <div className="bg-white shadow-sm border-b">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="flex justify-between items-center h-16">
                        <div className="flex items-center">
                            <div className="bg-orange-500 rounded-lg p-2">
                                <span className="text-white font-bold text-xl">SS</span>
                            </div>
                            <div className="ml-3">
                                <h1 className="text-xl font-semibold text-gray-900">Orders Management</h1>
                                <p className="text-sm text-gray-500">Manage your canteen orders</p>
                            </div>
                        </div>
                        <button 
                            onClick={() => window.history.back()}
                            className="border border-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-50 transition-colors"
                        >
                            Back to Dashboard
                        </button>
                    </div>
                </div>
            </div>

            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                {/* Tabs */}
                <div className="flex space-x-1 mb-8 bg-gray-100 rounded-lg p-1">
                    <button
                        onClick={() => setActiveTab('current')}
                        className={`flex-1 py-2 px-4 rounded-md text-sm font-medium transition-colors ${
                            activeTab === 'current'
                                ? 'bg-white text-orange-600 shadow-sm'
                                : 'text-gray-600 hover:text-gray-900'
                        }`}
                    >
                        Current Orders ({currentOrders.length})
                    </button>
                    <button
                        onClick={() => setActiveTab('today')}
                        className={`flex-1 py-2 px-4 rounded-md text-sm font-medium transition-colors ${
                            activeTab === 'today'
                                ? 'bg-white text-orange-600 shadow-sm'
                                : 'text-gray-600 hover:text-gray-900'
                        }`}
                    >
                        Today's Orders ({todayOrders.length})
                    </button>
                </div>

                {/* Orders Grid */}
                {activeOrders.length === 0 ? (
                    <div className="text-center py-12">
                        <div className="text-6xl mb-4">📋</div>
                        <h3 className="text-lg font-medium text-gray-900 mb-2">
                            {activeTab === 'current' ? 'No Active Orders' : 'No Orders Today'}
                        </h3>
                        <p className="text-gray-500">
                            {activeTab === 'current' 
                                ? 'All orders are completed!' 
                                : 'No orders have been placed today yet.'
                            }
                        </p>
                    </div>
                ) : (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        {activeOrders.map(renderOrderCard)}
                    </div>
                )}
            </div>

            {/* Order Details Dialog */}
            {isDialogOpen && selectedOrder && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
                    <div className="bg-white rounded-xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
                        {/* Dialog Header */}
                        <div className="flex justify-between items-center p-6 border-b">
                            <div className="flex items-center space-x-3">
                                <h2 className="text-xl font-semibold text-gray-900">Order Details</h2>
                                <span className={`px-3 py-1 rounded-full text-xs font-medium border flex items-center space-x-1 ${getStatusColor(selectedOrder.status)}`}>
                                    {getStatusIcon(selectedOrder.status)}
                                    <span className="capitalize">{selectedOrder.status}</span>
                                </span>
                            </div>
                            <button 
                                onClick={closeOrderDetails}
                                className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
                            >
                                <XMarkIcon className="h-5 w-5" />
                            </button>
                        </div>

                        {/* Dialog Body */}
                        <div className="p-6 space-y-6">
                            {/* Order Info */}
                            <div className="grid grid-cols-2 gap-4">
                                <div>
                                    <p className="text-sm text-gray-500 mb-1">Order ID</p>
                                    <p className="font-semibold">#{selectedOrder._id.slice(-8).toUpperCase()}</p>
                                </div>
                                <div>
                                    <p className="text-sm text-gray-500 mb-1">Order Time</p>
                                    <p className="font-semibold">{formatTime(selectedOrder.createdAt)}</p>
                                </div>
                                <div>
                                    <p className="text-sm text-gray-500 mb-1">Pickup Slot</p>
                                    <p className="font-semibold">{selectedOrder.timeSlot || 'No slot specified'}</p>
                                </div>
                                <div>
                                    <p className="text-sm text-gray-500 mb-1">Total Amount</p>
                                    <p className="text-xl font-bold text-orange-600">₹{selectedOrder.totalPrice}</p>
                                </div>
                            </div>

                            {/* Customer Info */}
                            <div className="bg-gray-50 rounded-lg p-4">
                                <div className="flex items-center space-x-3 mb-3">
                                    <UserIcon className="h-5 w-5 text-gray-400" />
                                    <h3 className="font-semibold text-gray-900">Customer Information</h3>
                                </div>
                                <div className="grid grid-cols-1 gap-2">
                                    <p><span className="text-gray-500">Name:</span> {selectedOrder.userId?.username || 'Not provided'}</p>
                                    <p><span className="text-gray-500">Email:</span> {selectedOrder.userId?.emailId || 'Not provided'}</p>
                                    <p><span className="text-gray-500">Mobile:</span> {selectedOrder.userId?.mobile || 'Not provided'}</p>
                                </div>
                            </div>

                            {/* Dishes */}
                            <div>
                                <h3 className="font-semibold text-gray-900 mb-4 flex items-center space-x-2">
                                    <TagIcon className="h-5 w-5" />
                                    <span>Order Items ({selectedOrder.dishes?.length || 0})</span>
                                </h3>
                                <div className="space-y-3">
                                    {selectedOrder.dishes?.map((dish, index) => (
                                        <div key={index} className="flex items-center space-x-4 p-3 border border-gray-200 rounded-lg">
                                            <div className="w-16 h-16 bg-gray-200 rounded-lg overflow-hidden">
                                                {dish.dish?.image ? (
                                                    <img 
                                                        src={dish.dish.image} 
                                                        alt={dish.dish.name}
                                                        className="w-full h-full object-cover"
                                                    />
                                                ) : (
                                                    <div className="w-full h-full flex items-center justify-center">
                                                        <TagIcon className="h-6 w-6 text-gray-400" />
                                                    </div>
                                                )}
                                            </div>
                                            <div className="flex-1">
                                                <h4 className="font-semibold text-gray-900">
                                                    {dish.dish?.name || 'Unknown Item'}
                                                </h4>
                                                <p className="text-sm text-gray-500">
                                                    {dish.dish?.category || 'No category'}
                                                </p>
                                                <p className="text-sm text-gray-600">
                                                    Quantity: {dish.quantity} × ₹{dish.price || dish.dish?.price || 0}
                                                </p>
                                            </div>
                                            <div className="text-right">
                                                <p className="font-semibold text-gray-900">
                                                    ₹{(dish.quantity * (dish.price || dish.dish?.price || 0))}
                                                </p>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </div>

                            {/* Status Update Actions */}
                            <div className="flex space-x-3 pt-4 border-t">
                                {selectedOrder.status === 'in queue' && (
                                    <>
                                        <button
                                            onClick={() => {
                                                updateOrderStatus(selectedOrder._id, 'preparing');
                                                closeOrderDetails();
                                            }}
                                            className="flex-1 bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600 transition-colors"
                                        >
                                            Start Preparing
                                        </button>
                                        <button
                                            onClick={() => {
                                                updateOrderStatus(selectedOrder._id, 'cancelled');
                                                closeOrderDetails();
                                            }}
                                            className="px-4 py-2 border border-red-300 text-red-600 rounded-lg hover:bg-red-50 transition-colors"
                                        >
                                            Cancel
                                        </button>
                                    </>
                                )}

                                {selectedOrder.status === 'preparing' && (
                                    <button
                                        onClick={() => {
                                            updateOrderStatus(selectedOrder._id, 'ready for pickup');
                                            closeOrderDetails();
                                        }}
                                        className="flex-1 bg-green-500 text-white py-2 px-4 rounded-lg hover:bg-green-600 transition-colors"
                                    >
                                        Mark as Ready
                                    </button>
                                )}

                                {selectedOrder.status === 'ready for pickup' && (
                                    <button
                                        onClick={() => {
                                            updateOrderStatus(selectedOrder._id, 'completed');
                                            closeOrderDetails();
                                        }}
                                        className="flex-1 bg-gray-500 text-white py-2 px-4 rounded-lg hover:bg-gray-600 transition-colors"
                                    >
                                        Mark as Completed
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Orders;
