// src/components/CanteenDashboard.js
import React, { useState, useEffect } from 'react';
import { 
    ShoppingBagIcon, 
    CurrencyRupeeIcon, 
    ClockIcon, 
    FireIcon,
    EyeIcon,
    Bars3Icon,
    BellIcon,
    UserCircleIcon,
    PlusIcon
} from '@heroicons/react/24/outline';
import { Link } from 'react-router-dom';

const CanteenDashboard = () => {
    const [dashboardData, setDashboardData] = useState({
        todayOrders: 0,
        todayRevenue: 0,
        activeOrders: 0,
        popularItem: { name: 'Loading...', count: 0 }
    });
    const [currentOrders, setCurrentOrders] = useState([]);
    const [weeklyRevenue, setWeeklyRevenue] = useState([]);
    const [canteenName, setCanteenName] = useState("");
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [sidebarOpen, setSidebarOpen] = useState(false);
    const [refreshing, setRefreshing] = useState(false);

    // Get canteen ID from localStorage (stored during login)
    const canteenId = localStorage.getItem('canteenId') || '689b8131d6a5e14a15e8e0bf'; // fallback ID

    useEffect(() => {
        fetchAllData();
        
        // Auto-refresh every 30 seconds
        const interval = setInterval(() => {
            fetchCurrentOrders();
        }, 30000);

        return () => clearInterval(interval);
    }, [canteenId]);

    const fetchAllData = async () => {
        setLoading(true);
        try {
            await Promise.all([
                fetchDashboardData(),
                fetchCurrentOrders(),
                fetchWeeklyRevenue()
            ]);
        } catch (error) {
            setError('Failed to load dashboard data');
        } finally {
            setLoading(false);
        }
    };

    const fetchDashboardData = async () => {
        try {
            const response = await fetch(`http://localhost:3000/api/get-canteen-dashboard/${canteenId}`);
            const result = await response.json();
            if (result.success) {
                setDashboardData(result.data);
                setCanteenName(result.data.canteenDetails.name)

            }
        } catch (error) {
            console.error('Error fetching dashboard data:', error);
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

    const fetchWeeklyRevenue = async () => {
        try {
            const response = await fetch(`http://localhost:3000/api/get-weekly-revenue/${canteenId}`);
            const result = await response.json();
            if (result.success) {
                setWeeklyRevenue(result.data || []);
            }
        } catch (error) {
            console.error('Error fetching weekly revenue:', error);
        }
    };

    const handleRefresh = async () => {
        setRefreshing(true);
        await fetchAllData();
        setRefreshing(false);
    };

    const updateOrderStatus = async (orderId, newStatus) => {
        try {
            const response = await fetch(`http://localhost:3000/api/orders/${canteenId}/updateOrderStatus`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ orderId, status: newStatus })
            });
            
            if (response.ok) {
                await fetchCurrentOrders();
                await fetchDashboardData();
            }
        } catch (error) {
            console.error('Error updating order status:', error);
        }
    };

    const getStatusColor = (status) => {
        switch (status) {
            case 'pending': return 'bg-yellow-100 text-yellow-800';
            case 'preparing': return 'bg-blue-100 text-blue-800';
            case 'ready for pickup': return 'bg-green-100 text-green-800';
            case 'completed': return 'bg-gray-100 text-gray-800';
            default: return 'bg-gray-100 text-gray-800';
        }
    };

    const formatTime = (dateString) => {
        return new Date(dateString).toLocaleTimeString('en-IN', {
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-50 flex items-center justify-center">
                <div className="text-center">
                    <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-orange-500 mx-auto mb-4"></div>
                    <p className="text-gray-600">Loading dashboard...</p>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="min-h-screen bg-gray-50 flex items-center justify-center">
                <div className="text-center">
                    <div className="bg-red-100 rounded-full p-3 mx-auto mb-4 w-fit">
                        <svg className="h-8 w-8 text-red-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L3.732 16.5c-.77.833.192 2.5 1.732 2.5z" />
                        </svg>
                    </div>
                    <h3 className="text-lg font-semibold text-gray-900 mb-2">Error Loading Dashboard</h3>
                    <p className="text-gray-600 mb-4">{error}</p>
                    <button 
                        onClick={fetchAllData}
                        className="bg-orange-500 text-white px-4 py-2 rounded-lg hover:bg-orange-600 transition-colors"
                    >
                        Try Again
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-gray-50">
            {/* Header */}
            <header className="bg-white shadow-sm border-b sticky top-0 z-40">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="flex justify-between items-center h-16">
                        <div className="flex items-center">
                            <button
                                onClick={() => setSidebarOpen(!sidebarOpen)}
                                className="p-2 rounded-lg hover:bg-gray-100 transition-colors lg:hidden"
                            >
                                <Bars3Icon className="h-6 w-6" />
                            </button>
                            <div className="flex items-center ml-2">
                                <div className="bg-orange-500 rounded-lg p-2">
                                    <span className="text-white font-bold text-xl">SS</span>
                                </div>
                                <div className="ml-3">
                                    <h1 className="text-xl font-semibold text-gray-900">Dashboard</h1>
                                    <p className="text-sm text-gray-500">{canteenName}</p>
                                </div>
                            </div>
                        </div>

                        <div className="flex items-center space-x-4">
                            <button 
                                onClick={handleRefresh}
                                className={`p-2 rounded-lg hover:bg-gray-100 transition-colors ${refreshing ? 'animate-spin' : ''}`}
                            >
                                {/* <RefreshIcon className="h-5 w-5 text-gray-600" /> */}Refresh
                            </button>
                            <div className="relative">

                                 <Link to="/profile"className="flex items-center space-x-2 p-2 rounded-lg hover:bg-gray-100 transition-colors">
                                                            <UserCircleIcon className="h-4 w-4" /><
                                                                span>Profile</span>
                                                            </Link>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </header>

            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                {/* Quick Actions */}
                <div className="mb-6">
                    <div className="flex flex-wrap gap-3">
                        <Link to="/orders" className="border border-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-50 transition-colors">
                            View All Orders
                        </Link>
                        <Link to="/manage-menu">
                            <button className="border border-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-50 transition-colors">
                            Manage Menu
                        </button>
                        </Link>
                        
                    </div>
                </div>

                {/* Stats Cards */}
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
                    <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 hover:shadow-md transition-shadow">
                        <div className="flex items-center">
                            <div className="bg-orange-100 rounded-lg p-3">
                                <ShoppingBagIcon className="h-6 w-6 text-orange-600" />
                            </div>
                            <div className="ml-4">
                                <p className="text-sm font-medium text-gray-500">Today's Orders</p>
                                <p className="text-2xl font-bold text-gray-900">{dashboardData.todayOrders}</p>
                                <p className="text-xs text-green-600 mt-1">↗ +12% from yesterday</p>
                            </div>
                        </div>
                    </div>

                    <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 hover:shadow-md transition-shadow">
                        <div className="flex items-center">
                            <div className="bg-green-100 rounded-lg p-3">
                                <CurrencyRupeeIcon className="h-6 w-6 text-green-600" />
                            </div>
                            <div className="ml-4">
                                <p className="text-sm font-medium text-gray-500">Today's Revenue</p>
                                <p className="text-2xl font-bold text-gray-900">₹{dashboardData.todayRevenue?.toLocaleString() || 0}</p>
                                <p className="text-xs text-green-600 mt-1">↗ +8% from yesterday</p>
                            </div>
                        </div>
                    </div>

                    <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 hover:shadow-md transition-shadow">
                        <div className="flex items-center">
                            <div className="bg-blue-100 rounded-lg p-3">
                                <ClockIcon className="h-6 w-6 text-blue-600" />
                            </div>
                            <div className="ml-4">
                                <p className="text-sm font-medium text-gray-500">Active Orders</p>
                                <p className="text-2xl font-bold text-gray-900">{dashboardData.activeOrders}</p>
                                <p className="text-xs text-blue-600 mt-1">Currently processing</p>
                            </div>
                        </div>
                    </div>

                    <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 hover:shadow-md transition-shadow">
                        <div className="flex items-center">
                            <div className="bg-red-100 rounded-lg p-3">
                                <FireIcon className="h-6 w-6 text-red-600" />
                            </div>
                            <div className="ml-4">
                                <p className="text-sm font-medium text-gray-500">Popular Today</p>
                                <p className="text-lg font-bold text-gray-900">{dashboardData.popularItem?.name}</p>
                                <p className="text-xs text-red-600 mt-1">{dashboardData.popularItem?.count} orders</p>
                            </div>
                        </div>
                    </div>
                </div>

                {/* Main Content Grid */}
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                    {/* Current Orders - Takes 2 columns */}
                    <div className="lg:col-span-2">
                        <div className="bg-white rounded-xl shadow-sm border border-gray-100">
                            <div className="px-6 py-4 border-b border-gray-100">
                                <div className="flex justify-between items-center">
                                    <div className="flex items-center space-x-3">
                                        <h2 className="text-lg font-semibold text-gray-900">Live Order Queue</h2>
                                        <div className="flex items-center space-x-2">
                                            <div className="bg-green-500 rounded-full h-2 w-2 animate-pulse"></div>
                                            <span className="text-sm text-green-600">Live</span>
                                        </div>
                                    </div>
                                    <span className="bg-gray-100 text-gray-800 px-3 py-1 rounded-full text-sm font-medium">
                                        {currentOrders.length} active
                                    </span>
                                </div>
                            </div>

                            <div className="p-6">
                                {currentOrders.length === 0 ? (
                                    <div className="text-center py-12">
                                        <ClockIcon className="h-16 w-16 text-gray-300 mx-auto mb-4" />
                                        <h3 className="text-lg font-medium text-gray-900 mb-2">No Active Orders</h3>
                                        <p className="text-gray-500">All caught up! New orders will appear here.</p>
                                    </div>
                                ) : (
                                    <div className="space-y-4 max-h-96 overflow-y-auto">
                                        {currentOrders.map((order) => (
                                            <div key={order._id} className="border border-gray-200 rounded-lg p-4 hover:shadow-sm transition-all hover:border-orange-200">
                                                <div className="flex justify-between items-start mb-3">
                                                    <div className="flex items-center space-x-3">
                                                        <div className="bg-orange-50 border border-orange-200 rounded-lg p-2">
                                                            <span className="text-sm font-bold text-orange-600">
                                                                #{order._id.slice(-6).toUpperCase()}
                                                            </span>
                                                        </div>
                                                        <div>
                                                            <p className="font-semibold text-gray-900">
                                                                {order.userId?.username || 'Anonymous Customer'}
                                                            </p>
                                                            <p className="text-sm text-gray-500">
                                                                {formatTime(order.createdAt)} • Slot: {order.timeSlot || 'No slot'}
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <span className={`px-3 py-1 rounded-full text-xs font-medium capitalize ${getStatusColor(order.status)}`}>
                                                        {order.status}
                                                    </span>
                                                </div>

                                                <div className="mb-4">
                                                    <p className="text-sm text-gray-600 mb-2 font-medium">Order Items:</p>
                                                    <div className="bg-gray-50 rounded-lg p-3">
                                                        {order.dishes && order.dishes.length > 0 ? (
                                                            <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
                                                                {order.dishes.map((dish, index) => (
                                                                    <div key={index} className="flex justify-between text-sm">
                                                                        <span className="font-medium text-gray-900">
                                                                            {dish.dish?.name || dish.foodName || 'Unknown Item'}
                                                                        </span>
                                                                        <span className="text-gray-500">×{dish.quantity}</span>
                                                                    </div>
                                                                ))}
                                                            </div>
                                                        ) : (
                                                            <p className="text-sm text-gray-500">No items found</p>
                                                        )}
                                                    </div>
                                                </div>

                                                <div className="flex justify-between items-center">
                                                    <div className="flex items-center space-x-2">
                                                        <span className="text-lg font-bold text-gray-900">₹{order.totalPrice}</span>
                                                    </div>
                                                    <div className="flex space-x-2">
                                                        {order.status === 'pending' && (
                                                            <button
                                                                onClick={() => updateOrderStatus(order._id, 'preparing')}
                                                                className="bg-blue-500 text-white px-3 py-1 rounded-lg text-sm hover:bg-blue-600 transition-colors"
                                                            >
                                                                Start Preparing
                                                            </button>
                                                        )}
                                                        {order.status === 'preparing' && (
                                                            <button
                                                                onClick={() => updateOrderStatus(order._id, 'ready for pickup')}
                                                                className="bg-green-500 text-white px-3 py-1 rounded-lg text-sm hover:bg-green-600 transition-colors"
                                                            >
                                                                Mark Ready
                                                            </button>
                                                        )}
                                                    </div>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>

                    {/* Right Sidebar - Analytics */}
                    <div className="space-y-6">
                        {/* Weekly Revenue Chart */}
                        <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
                            <h3 className="text-lg font-semibold text-gray-900 mb-4">Weekly Revenue</h3>
                            <div className="space-y-3">
                                {weeklyRevenue.slice(-7).map((day, index) => (
                                    <div key={index} className="flex justify-between items-center">
                                        <span className="text-sm text-gray-600">
                                            {new Date(day._id).toLocaleDateString('en-IN', { weekday: 'short', day: 'numeric' })}
                                        </span>
                                        <span className="font-semibold text-gray-900">₹{day.revenue?.toLocaleString() || 0}</span>
                                    </div>
                                ))}
                            </div>
                        </div>

                        {/* Quick Stats */}
                        <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
                            <h3 className="text-lg font-semibold text-gray-900 mb-4">Quick Stats</h3>
                            <div className="space-y-4">
                                <div className="flex justify-between">
                                    <span className="text-gray-600">Average Order Value</span>
                                    <span className="font-semibold">₹{dashboardData.todayOrders > 0 ? Math.round(dashboardData.todayRevenue / dashboardData.todayOrders) : 0}</span>
                                </div>
                                <div className="flex justify-between">
                                    <span className="text-gray-600">Peak Hour</span>
                                    <span className="font-semibold">12:00 - 1:00 PM</span>
                                </div>
                                <div className="flex justify-between">
                                    <span className="text-gray-600">Status</span>
                                    <span className="text-green-600 font-semibold">● Online</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CanteenDashboard;
