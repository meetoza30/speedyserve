import React from 'react';
import './Navbar.css';
import { assets } from '../../assets/assets.js';

const Navbar = ({setShowLogin}) => {
  return (
    <div className='navbar'>
      {/* Left side: SpeedyServe branding */}
      <h1 className="navbar-brand">SpeedyServe <span>(Admin Panel)</span></h1>
      
      {/* Right side: Sign in button and profile image */}
      <div className="navbar-right">
        <button onClick={() => setShowLogin(true)} className="signin-btn">Sign in</button>
        <img className="profile" src={assets.profile_image} alt="Profile" />
      </div>
    </div>
  );
};

export default Navbar;
