import React from 'react';
import './Navbar.css';
import { assets } from '../../assets/assets.js';
import {Link} from "react-router-dom";

const Navbar = ({setShowLogin}) => {
  return (
    <nav className="fixed top-0 w-full bg-white/90 backdrop-blur-md z-50 border-b border-gray-100">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center">
              <div className="bg-orange-500 rounded-lg p-2">
                <span className="text-white font-bold text-xl">SS</span>
              </div>
              <span className="ml-3 text-xl font-bold text-gray-900">SpeedyServe</span>
            </div>
            <div className="hidden md:flex space-x-8">
              <a href="#features" className="text-gray-600 hover:text-orange-500 transition-colors">Features</a>
              <a href="#how-it-works" className="text-gray-600 hover:text-orange-500 transition-colors">How It Works</a>
              <a href="#canteen" className="text-gray-600 hover:text-orange-500 transition-colors">For Canteens</a>
              <a href="#download" className="text-gray-600 hover:text-orange-500 transition-colors">Download</a>
            </div>
            <Link to="/login">
            <button className="bg-orange-500 text-white px-6 py-2 rounded-full hover:bg-orange-600 transition-all duration-300 hover:scale-105">
              Login
            </button>
            </Link>

          </div>
        </div>
      </nav>
  );
};

export default Navbar;
