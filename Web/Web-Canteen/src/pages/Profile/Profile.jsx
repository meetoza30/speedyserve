// src/components/CanteenProfile.js
import React, { useState, useEffect } from 'react';
import { 
    UserIcon, 
    EnvelopeIcon, 
    PhoneIcon, 
    ClockIcon,
    BuildingStorefrontIcon,
    PencilIcon,
    CheckIcon,
    XMarkIcon, UserCircleIcon
} from '@heroicons/react/24/outline';

const CanteenProfile = () => {
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false);
    const [editForm, setEditForm] = useState({});
    const [updating, setUpdating] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const canteenId = localStorage.getItem('canteenId') || '689b8131d6a5e14a15e8e0bf';

    useEffect(() => {
        fetchProfile();
    }, [canteenId]);

    const fetchProfile = async () => {
        try {
            setLoading(true);
            const response = await fetch(`http://localhost:3000/api/profile/${canteenId}`);
            const result = await response.json();
            
            if (result.success) {
                setProfile(result.data);
                setEditForm(result.data);
                setError('');
            } else {
                setError(result.message || 'Failed to fetch profile');
            }
        } catch (error) {
            setError('Network error occurred');
            console.error('Error fetching profile:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEditForm(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleEdit = () => {
        setIsEditing(true);
        setEditForm(profile);
        setError('');
        setSuccess('');
    };

    const handleCancel = () => {
        setIsEditing(false);
        setEditForm(profile);
        setError('');
        setSuccess('');
    };

    const handleSave = async () => {
        try {
            setUpdating(true);
            setError('');

            const response = await fetch(`http://localhost:3000/api/profile/${canteenId}`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(editForm)
            });

            const result = await response.json();
            
            if (result.success) {
                setProfile(result.data);
                setIsEditing(false);
                setSuccess('Profile updated successfully!');
                setTimeout(() => setSuccess(''), 3000);
            } else {
                setError(result.message || 'Failed to update profile');
            }
        } catch (error) {
            setError('Network error occurred');
            console.error('Error updating profile:', error);
        } finally {
            setUpdating(false);
        }
    };

     const handleLogout = () => {
        localStorage.removeItem('user');
        localStorage.removeItem('canteenId');
        window.location.href = '/login';
    };

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-50 flex items-center justify-center">
                <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-orange-500"></div>
            </div>
        );
    }

    if (!profile) {
        return (
            <div className="min-h-screen bg-gray-50 flex items-center justify-center">
                <div className="text-center">
                    <h3 className="text-lg font-semibold text-gray-900 mb-2">Profile Not Found</h3>
                    <p className="text-gray-500">Unable to load canteen profile</p>
                </div>
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
                                <h1 className="text-xl font-semibold text-gray-900">Profile</h1>
                                <p className="text-sm text-gray-500">Manage your canteen information</p>
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

            <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                {/* Success/Error Messages */}
                {success && (
                    <div className="mb-6 bg-green-50 border border-green-200 text-green-700 px-4 py-3 rounded-lg">
                        {success}
                    </div>
                )}

                {error && (
                    <div className="mb-6 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
                        {error}
                    </div>
                )}

                {/* Profile Card */}
                <div className="bg-white rounded-xl shadow-sm border border-gray-200">
                    {/* Card Header */}
                    <div className="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
                        <div className="flex items-center space-x-3">
                            <BuildingStorefrontIcon className="h-6 w-6 text-orange-500" />
                            <h2 className="text-lg font-semibold text-gray-900">Canteen Information</h2>
                        </div>
                        
                        {!isEditing ? (
                            <div className='flex flex-row items-center justify-between' >

                                <button
                                onClick={handleEdit}
                                className="flex items-center space-x-2 px-4 py-2 bg-orange-500 text-white rounded-lg hover:bg-orange-600 transition-colors"
                            >
                                <PencilIcon className="h-4 w-4" />
                                <span>Edit Profile</span>
                            </button>

                            <button 
                                                                onClick={handleLogout}
                                                                className="flex items-center space-x-2 mx-4 px-4 py-2 bg-orange-500 text-white rounded-lg hover:bg-orange-600 transition-colors"
                                                            >
                                                                <UserCircleIcon className="h-4 w-4" />
                                                                <span>Logout</span>
                                                            </button>
                            </div>
                            

                            
                        ) : (
                            <div className="flex space-x-2">
                                <button
                                    onClick={handleCancel}
                                    className="flex items-center space-x-2 px-4 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors"
                                >
                                    <XMarkIcon className="h-4 w-4" />
                                    <span>Cancel</span>
                                </button>
                                <button
                                    onClick={handleSave}
                                    disabled={updating}
                                    className="flex items-center space-x-2 px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors disabled:opacity-50"
                                >
                                    <CheckIcon className="h-4 w-4" />
                                    <span>{updating ? 'Saving...' : 'Save Changes'}</span>
                                </button>
                            </div>
                        )}
                    </div>

                    {/* Card Body */}
                    <div className="p-6">
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            {/* Canteen Name */}
                            <div>
                                <label className="flex items-center text-sm font-medium text-gray-700 mb-2">
                                    <UserIcon className="h-4 w-4 mr-2 text-gray-400" />
                                    Canteen Name
                                </label>
                                {isEditing ? (
                                    <input
                                        type="text"
                                        name="name"
                                        value={editForm.name || ''}
                                        onChange={handleInputChange}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                        placeholder="Enter canteen name"
                                    />
                                ) : (
                                    <p className="px-3 py-2 bg-gray-50 rounded-lg text-gray-900">
                                        {profile.name || 'Not specified'}
                                    </p>
                                )}
                            </div>

                            {/* Email */}
                            <div>
                                <label className="flex items-center text-sm font-medium text-gray-700 mb-2">
                                    <EnvelopeIcon className="h-4 w-4 mr-2 text-gray-400" />
                                    Email Address
                                </label>
                                {isEditing ? (
                                    <input
                                        type="email"
                                        name="emailId"
                                        value={editForm.emailId || ''}
                                        onChange={handleInputChange}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                        placeholder="Enter email address"
                                    />
                                ) : (
                                    <p className="px-3 py-2 bg-gray-50 rounded-lg text-gray-900">
                                        {profile.emailId || 'Not specified'}
                                    </p>
                                )}
                            </div>

                            {/* Mobile */}
                            <div>
                                <label className="flex items-center text-sm font-medium text-gray-700 mb-2">
                                    <PhoneIcon className="h-4 w-4 mr-2 text-gray-400" />
                                    Mobile Number
                                </label>
                                {isEditing ? (
                                    <input
                                        type="tel"
                                        name="mobile"
                                        value={editForm.mobile || ''}
                                        onChange={handleInputChange}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                        placeholder="Enter mobile number"
                                    />
                                ) : (
                                    <p className="px-3 py-2 bg-gray-50 rounded-lg text-gray-900">
                                        {profile.mobile || 'Not specified'}
                                    </p>
                                )}
                            </div>

                            {/* Number of Stoves */}
                            <div>
                                <label className="flex items-center text-sm font-medium text-gray-700 mb-2">
                                    <BuildingStorefrontIcon className="h-4 w-4 mr-2 text-gray-400" />
                                    Number of Stoves
                                </label>
                                {isEditing ? (
                                    <input
                                        type="number"
                                        name="stoves"
                                        value={editForm.stoves || ''}
                                        onChange={handleInputChange}
                                        min="1"
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                        placeholder="Enter number of stoves"
                                    />
                                ) : (
                                    <p className="px-3 py-2 bg-gray-50 rounded-lg text-gray-900">
                                        {profile.stoves || '1'}
                                    </p>
                                )}
                            </div>

                            {/* Opening Time */}
                            <div>
                                <label className="flex items-center text-sm font-medium text-gray-700 mb-2">
                                    <ClockIcon className="h-4 w-4 mr-2 text-gray-400" />
                                    Opening Time
                                </label>
                                {isEditing ? (
                                    <input
                                        type="time"
                                        name="openingTime"
                                        value={editForm.openingTime || ''}
                                        onChange={handleInputChange}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                    />
                                ) : (
                                    <p className="px-3 py-2 bg-gray-50 rounded-lg text-gray-900">
                                        {profile.openingTime || 'Not specified'}
                                    </p>
                                )}
                            </div>

                            {/* Closing Time */}
                            <div>
                                <label className="flex items-center text-sm font-medium text-gray-700 mb-2">
                                    <ClockIcon className="h-4 w-4 mr-2 text-gray-400" />
                                    Closing Time
                                </label>
                                {isEditing ? (
                                    <input
                                        type="time"
                                        name="closingTime"
                                        value={editForm.closingTime || ''}
                                        onChange={handleInputChange}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-orange-500 focus:border-orange-500"
                                    />
                                ) : (
                                    <p className="px-3 py-2 bg-gray-50 rounded-lg text-gray-900">
                                        {profile.closingTime || 'Not specified'}
                                    </p>
                                )}
                            </div>
                        </div>

                        {/* Account Info */}
                        <div className="mt-8 pt-6 border-t border-gray-200">
                            <h3 className="text-sm font-medium text-gray-700 mb-4">Account Information</h3>
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <div>
                                    <label className="text-sm font-medium text-gray-700">Account Created</label>
                                    <p className="px-3 py-2 bg-gray-50 rounded-lg text-gray-900 mt-1">
                                        {profile.createdAt ? new Date(profile.createdAt).toLocaleDateString('en-IN') : 'Unknown'}
                                    </p>
                                </div>
                                <div>
                                    <label className="text-sm font-medium text-gray-700">Last Updated</label>
                                    <p className="px-3 py-2 bg-gray-50 rounded-lg text-gray-900 mt-1">
                                        {profile.updatedAt ? new Date(profile.updatedAt).toLocaleDateString('en-IN') : 'Unknown'}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CanteenProfile;
