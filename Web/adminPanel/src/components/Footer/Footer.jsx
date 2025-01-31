import React from 'react'
import './Footer.css'
import { assets } from '../../assets/assets.js'

const Footer = () => {
  return (
    <div className='footer' id='footer'>
      <div className="footer-content">
        <div className="footer-content-left">
            {/* <img src={assets.logo} alt="" /> */}
            <h1>SpeedyServe</h1>
            <p>Order Ahead, Save Time!</p>
            <div className="footer-social-icons">
                <img src={assets.facebook_icon} alt="" />
                <img src={assets.twitter_icon} alt="" />
                <img src={assets.linkedin_icon} alt="" />
            </div>
        </div>
        <div className="footer-content-center">
            <h2>COMPANY</h2>
            <ul>
                <li>Add item</li>
                <li>List items</li>
                <li>Orders</li>
               
            </ul>
        </div>
        <div className="footer-content-right">
            <h2>GET IN TOUCH</h2>
            <ul>
                <li>9322714198</li>
                <li>contact@speedyserve.com</li>
            </ul>
        </div>
      </div>
      <hr />
      <p className="footer-copyright">Copyright 2025 © SpeedyServe - All Right Reserved.</p>
    </div>
  )
}

export default Footer