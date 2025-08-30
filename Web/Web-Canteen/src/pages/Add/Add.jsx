// src/components/ManageMenu.js
import React, { useState, useEffect } from 'react';
import { 
    PlusIcon, 
    PencilIcon, 
    TrashIcon, 
    XMarkIcon,
    PhotoIcon,
    CurrencyRupeeIcon,
    ClockIcon,
    TagIcon
} from '@heroicons/react/24/outline';

const ManageMenu = () => {
    const [dishes, setDishes] = useState([]);
    const [canteen, setCanteen] = useState({});
    const [loading, setLoading] = useState(true);
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [isEditMode, setIsEditMode] = useState(false);
    const [editingDishId, setEditingDishId] = useState(null);
    const [formData, setFormData] = useState({
        name: '',
        description: '',
        price: '',
        category: '',
        serveTime: '',
        image: null
    });
    const [imagePreview, setImagePreview] = useState(null);
    const [submittingDish, setSubmittingDish] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const canteenId = localStorage.getItem('canteenId') || '689b8131d6a5e14a15e8e0bf';

    useEffect(() => {
        fetchDishes();
    }, [canteenId]);

    const fetchDishes = async () => {
        try {
            setLoading(true);
            const response = await fetch('http://localhost:3000/api/getCanteenDishes', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ canteenId })
            });

            const result = await response.json();
            if (result.success) {
                setDishes(result.dishes || []);
                setCanteen(result.canteen || {});
            }
        } catch (error) {
            console.error('Error fetching dishes:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
        if (error) setError('');
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setFormData(prev => ({
                ...prev,
                image: file
            }));
            
            // Create preview
            const reader = new FileReader();
            reader.onloadend = () => {
                setImagePreview(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const validateForm = () => {
        if (!formData.name.trim()) {
            setError('Dish name is required');
            return false;
        }
        if (!formData.description.trim()) {
            setError('Description is required');
            return false;
        }
        if (!formData.price || formData.price <= 0) {
            setError('Valid price is required');
            return false;
        }
        if (!formData.category.trim()) {
            setError('Category is required');
            return false;
        }
        if (!formData.serveTime || formData.serveTime <= 0) {
            setError('Serve time is required');
            return false;
        }
        return true;
    };

const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) return;

    setSubmittingDish(true);
    setError('');

    try {
        if (isEditMode) {
            if (formData.image) {
                const formDataToSend = new FormData();
                formDataToSend.append('canteenId', canteenId);
                formDataToSend.append('dishId', editingDishId);
                formDataToSend.append('name', formData.name);
                formDataToSend.append('description', formData.description);
                formDataToSend.append('price', formData.price);
                formDataToSend.append('category', formData.category);
                formDataToSend.append('serveTime', formData.serveTime);
                formDataToSend.append('image', formData.image);

                const response = await fetch('http://localhost:3000/api/updateDish', {
                    method: 'POST',
                    body: formDataToSend
                });

                // DEBUG: Log response details
                console.log('Response status:', response.status);
                console.log('Response headers:', response.headers);
                
                // Get response text first to inspect
                const responseText = await response.text();
                console.log('Raw response:', responseText);
                
                // Try to parse as JSON
                let result;
                try {
                    result = JSON.parse(responseText);
                } catch (jsonError) {
                    console.error('JSON parse error:', jsonError);
                    console.error('Response was:', responseText);
                    setError('Server returned an error. Check console for details.');
                    return;
                }
                
                if (result.success) {
                    setSuccess('Dish updated successfully!');
                    resetForm();
                    setIsDialogOpen(false);
                    fetchDishes();
                } else {
                    setError(result.message || 'Failed to update dish');
                }
            } else {
                // No image update logic (keep existing)
                const updateData = {
                    name: formData.name,
                    description: formData.description,
                    price: formData.price,
                    category: formData.category,
                    serveTime: formData.serveTime
                };

                const response = await fetch('http://localhost:3000/api/updateDish', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        canteenId,
                        dishId: editingDishId,
                        data: updateData
                    })
                });

                const result = await response.json();
                
                if (result.success) {
                    setSuccess('Dish updated successfully!');
                    resetForm();
                    setIsDialogOpen(false);
                    fetchDishes();
                } else {
                    setError(result.message || 'Failed to update dish');
                }
            }
        } else {
              // Add new dish (your existing code is correct)
            const formDataToSend = new FormData();
            formDataToSend.append('canteenId', canteenId);
            formDataToSend.append('name', formData.name);
            formDataToSend.append('description', formData.description);
            formDataToSend.append('price', formData.price);
            formDataToSend.append('category', formData.category);
            formDataToSend.append('serveTime', formData.serveTime);
            
            if (formData.image) {
                formDataToSend.append('image', formData.image);
            }

            const response = await fetch('http://localhost:3000/api/addDish', {
                method: 'POST',
                body: formDataToSend
            });

            const result = await response.json();
            
            if (result.success) {
                setSuccess('Dish added successfully!');
                resetForm();
                setIsDialogOpen(false);
                fetchDishes();
            } else {
                setError(result.message || 'Failed to add dish');
            }

        }
    } catch (error) {
        setError('Network error. Please try again.');
        console.error('Submit dish error:', error);
    } finally {
        setSubmittingDish(false);
    }
};



    const handleRemoveDish = async (dishId) => {
        if (!window.confirm('Are you sure you want to remove this dish?')) return;

        try {
            const response = await fetch('http://localhost:3000/api/removeDish', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ canteenId, dishId })
            });

            const result = await response.json();
            if (result.success) {
                setSuccess('Dish removed successfully!');
                fetchDishes();
            } else {
                setError(result.message || 'Failed to remove dish');
            }
        } catch (error) {
            setError('Network error. Please try again.');
            console.error('Remove dish error:', error);
        }
    };

    const openAddDialog = () => {
        resetForm();
        setIsEditMode(false);
        setEditingDishId(null);
        setIsDialogOpen(true);
    };

    const openEditDialog = (dish) => {
        setFormData({
            name: dish.name,
            description: dish.description,
            price: dish.price,
            category: dish.category,
            serveTime: dish.serveTime,
            image: null // Don't pre-fill image file
        });
        setImagePreview(dish.image); // Show existing image
        setIsEditMode(true);
        setEditingDishId(dish._id);
        setIsDialogOpen(true);
        setError('');
        setSuccess('');
    };

    const resetForm = () => {
        setFormData({
            name: '',
            description: '',
            price: '',
            category: '',
            serveTime: '',
            image: null
        });
        setImagePreview(null);
        setIsEditMode(false);
        setEditingDishId(null);
        setError('');
        setSuccess('');
    };

    const closeDialog = () => {
        setIsDialogOpen(false);
        resetForm();
    };

    const categories = ['Breakfast', 'Lunch', 'Dinner', 'Snacks', 'Beverages', 'Desserts', 'Fast Food'];

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
                                <h1 className="text-xl font-semibold text-gray-900">Manage Menu</h1>
                                <p className="text-sm text-gray-500">{canteen.name || 'Your Canteen'}</p>
                            </div>
                        </div>
                        <div className="flex space-x-4">
                            <button 
                                onClick={() => window.history.back()}
                                className="border border-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-50 transition-colors"
                            >
                                Back to Dashboard
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            {/* Success/Error Messages */}
            {success && (
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-6">
                    <div className="bg-green-50 border border-green-200 text-green-700 px-4 py-3 rounded-lg mb-4">
                        {success}
                        <button 
                            onClick={() => setSuccess('')} 
                            className="float-right font-bold text-green-700 hover:text-green-900"
                        >
                            ×
                        </button>
                    </div>
                </div>
            )}

            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                {/* Add Dish Button */}
                <div className="mb-8">
                    <div className="flex justify-between items-center">
                        <div>
                            <h2 className="text-2xl font-bold text-gray-900">Your Dishes</h2>
                            <p className="text-gray-600">{dishes.length} dishes in your menu</p>
                        </div>
                        <button
                            onClick={openAddDialog}
                            className="bg-orange-500 text-white px-6 py-3 rounded-lg hover:bg-orange-600 transition-colors flex items-center space-x-2 shadow-lg hover:shadow-xl"
                        >
                            <PlusIcon className="h-5 w-5" />
                            <span>Add New Dish</span>
                        </button>
                    </div>
                </div>

                {/* Dishes Grid */}
                {dishes.length === 0 ? (
                    <div className="text-center py-12">
                        <PhotoIcon className="h-16 w-16 text-gray-300 mx-auto mb-4" />
                        <h3 className="text-lg font-medium text-gray-900 mb-2">No dishes yet</h3>
                        <p className="text-gray-500 mb-6">Start building your menu by adding your first dish!</p>
                        <button
                            onClick={openAddDialog}
                            className="bg-orange-500 text-white px-6 py-3 rounded-lg hover:bg-orange-600 transition-colors"
                        >
                            Add Your First Dish
                        </button>
                    </div>
                ) : (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                        {dishes.map((dish) => (
                            <div key={dish._id} className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden hover:shadow-md transition-shadow">
                                {/* Dish Image */}
                                <div className="aspect-w-16 aspect-h-12 bg-gray-200">
                                    {dish.image ? (
                                        <img
                                            src={dish.image}
                                            alt={dish.name}
                                            className="w-full h-48 object-cover"
                                        />
                                    ) : (
                                        <div className="w-full h-48 bg-gray-200 flex items-center justify-center">
                                            <PhotoIcon className="h-12 w-12 text-gray-400" />
                                        </div>
                                    )}
                                </div>

                                {/* Dish Details */}
                                <div className="p-4">
                                    <div className="flex justify-between items-start mb-2">
                                        <h3 className="font-semibold text-gray-900 truncate">{dish.name}</h3>
                                        <span className="bg-orange-100 text-orange-800 px-2 py-1 rounded-full text-xs font-medium">
                                            {dish.category}
                                        </span>
                                    </div>
                                    
                                    <p className="text-gray-600 text-sm mb-3 line-clamp-2">
                                        {dish.description}
                                    </p>

                                    <div className="flex items-center justify-between mb-4">
                                        <div className="flex items-center space-x-2">
                                            <CurrencyRupeeIcon className="h-4 w-4 text-green-600" />
                                            <span className="font-semibold text-gray-900">₹{dish.price}</span>
                                        </div>
                                        <div className="flex items-center space-x-1 text-gray-500 text-sm">
                                            <ClockIcon className="h-4 w-4" />
                                            <span>{dish.serveTime}min</span>
                                        </div>
                                    </div>

                                    {/* Action Buttons */}
                                    <div className="flex space-x-2">
                                        <button 
                                            onClick={() => openEditDialog(dish)}
                                            className="flex-1 bg-blue-50 text-blue-600 px-3 py-2 rounded-lg hover:bg-blue-100 transition-colors flex items-center justify-center space-x-1"
                                        >
                                            <PencilIcon className="h-4 w-4" />
                                            <span className="text-sm">Edit</span>
                                        </button>
                                        <button 
                                            onClick={() => handleRemoveDish(dish._id)}
                                            className="flex-1 bg-red-50 text-red-600 px-3 py-2 rounded-lg hover:bg-red-100 transition-colors flex items-center justify-center space-x-1"
                                        >
                                            <TrashIcon className="h-4 w-4" />
                                            <span className="text-sm">Remove</span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            {/* Add/Edit Dish Modal */}
            {isDialogOpen && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
                    <div className="bg-white rounded-xl max-w-md w-full max-h-[90vh] overflow-y-auto">
                        {/* Modal Header */}
                        <div className="flex justify-between items-center p-6 border-b">
                            <h3 className="text-lg font-semibold text-gray-900">
                                {isEditMode ? 'Edit Dish' : 'Add New Dish'}
                            </h3>
                            <button 
                                onClick={closeDialog}
                                className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
                            >
                                <XMarkIcon className="h-5 w-5" />
                            </button>
                        </div>

                        {/* Modal Body */}
                        <form onSubmit={handleSubmit} className="p-6 space-y-4">
                            {error && (
                                <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
                                    {error}
                                </div>
                            )}

                            {/* Image Upload */}
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    Dish Image
                                </label>
                                <div className="border-2 border-dashed border-gray-300 rounded-lg p-4 text-center hover:border-orange-500 transition-colors relative">
                                    {imagePreview ? (
                                        <div className="relative">
                                            <img 
                                                src={imagePreview} 
                                                alt="Preview" 
                                                className="w-full h-32 object-cover rounded-lg"
                                            />
                                            <button
                                                type="button"
                                                onClick={() => {
                                                    setImagePreview(null);
                                                    setFormData(prev => ({ ...prev, image: null }));
                                                }}
                                                className="absolute top-2 right-2 bg-red-500 text-white p-1 rounded-full hover:bg-red-600"
                                            >
                                                <XMarkIcon className="h-3 w-3" />
                                            </button>
                                        </div>
                                    ) : (
                                        <div>
                                            <PhotoIcon className="h-8 w-8 text-gray-400 mx-auto mb-2" />
                                            <p className="text-sm text-gray-500">
                                                {isEditMode ? 'Click to change dish image' : 'Click to upload dish image'}
                                            </p>
                                        </div>
                                    )}
                                    <input
                                        type="file"
                                        accept="image/*"
                                        onChange={handleImageChange}
                                        className="absolute inset-0 opacity-0 cursor-pointer"
                                    />
                                </div>
                            </div>

                            {/* Dish Name */}
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">
                                    Dish Name *
                                </label>
                                <input
                                    type="text"
                                    name="name"
                                    value={formData.name}
                                    onChange={handleInputChange}
                                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                    placeholder="e.g., Butter Chicken"
                                    required
                                />
                            </div>

                            {/* Description */}
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">
                                    Description *
                                </label>
                                <textarea
                                    name="description"
                                    value={formData.description}
                                    onChange={handleInputChange}
                                    rows="3"
                                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                    placeholder="Describe your dish..."
                                    required
                                />
                            </div>

                            {/* Price and Serve Time */}
                            <div className="grid grid-cols-2 gap-4">
                                <div>
                                    <label className="block text-sm font-medium text-gray-700 mb-1">
                                        Price (₹) *
                                    </label>
                                    <input
                                        type="number"
                                        name="price"
                                        value={formData.price}
                                        onChange={handleInputChange}
                                        min="1"
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                        placeholder="120"
                                        required
                                    />
                                </div>
                                <div>
                                    <label className="block text-sm font-medium text-gray-700 mb-1">
                                        Serve Time (min) *
                                    </label>
                                    <input
                                        type="number"
                                        name="serveTime"
                                        value={formData.serveTime}
                                        onChange={handleInputChange}
                                        min="1"
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                        placeholder="15"
                                        required
                                    />
                                </div>
                            </div>

                            {/* Category */}
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">
                                    Category *
                                </label>
                                <select
                                    name="category"
                                    value={formData.category}
                                    onChange={handleInputChange}
                                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                    required
                                >
                                    <option value="">Select a category</option>
                                    {categories.map(cat => (
                                        <option key={cat} value={cat}>{cat}</option>
                                    ))}
                                </select>
                            </div>

                            {/* Submit Buttons */}
                            <div className="flex space-x-4 pt-4">
                                <button
                                    type="button"
                                    onClick={closeDialog}
                                    className="flex-1 border border-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-50 transition-colors"
                                >
                                    Cancel
                                </button>
                                <button
                                    type="submit"
                                    disabled={submittingDish}
                                    className="flex-1 bg-orange-500 text-white px-4 py-2 rounded-lg hover:bg-orange-600 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                                >
                                    {submittingDish 
                                        ? (isEditMode ? 'Updating...' : 'Adding...') 
                                        : (isEditMode ? 'Update Dish' : 'Add Dish')
                                    }
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ManageMenu;
