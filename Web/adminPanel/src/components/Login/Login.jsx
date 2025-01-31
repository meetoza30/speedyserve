import React, { useState } from 'react';
import { assets } from '../../assets/assets.js';
import { useNavigate } from "react-router-dom";
import './Login.css';
import axios from 'axios'; // Import axios for making API calls
import { toast } from 'react-toastify'; // Import toast for showing notifications

const Login = ({ setShowLogin }) => {
  const [currState, setCurrState] = useState("Sign Up");
  const [formData, setFormData] = useState({
    name: "",
    emailId: "",
    mobile: "",
    password: "",
  });

  // Handle form input changes
  const onChangeHandler = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  // Handle registration form submission
  const handleRegisterSubmit = async (event) => {
    event.preventDefault();
    const { name, emailId, mobile, password } = formData;

    try {
      const response = await axios.post('http://localhost:3000/api/canteens/registerCanteen', { name, emailId, mobile, password });
      if (response.data === "Already registered") {
        toast.error("E-mail already registered! Please Login to proceed.");
        setCurrState("Login");
      } else {
        toast.success("Registered successfully! Please Login to proceed.");
        setShowLogin(false);
        // const navigate = useNavigate();
        // navigate("/add");
        // setCurrState("Login");
      }
    } catch (err) {
      console.log(err);
      toast.error("Enter the proper credentials");
    }
  };


  // Handle login form submission


const handleLoginSubmit = async (event) => {
  event.preventDefault();
  // const navigate = useNavigate(); // Initialize navigate function

  const { emailId, password } = formData;
  if (!emailId || !password) {
    toast.error("Please enter both email and password.");
    return;
  }

  try {
    const response = await axios.post("http://localhost:3000/api/canteens/loginCanteen", { 
      emailId, 
      password 
    });

    const { success, message } = response.data;

    if (success) {
      toast.success("Login successful!");
      setShowLogin(false);

      // Close login popup after a short delay and navigate to /additem
      // setTimeout(() => {
      //   setShowLogin(false);
      //   navigate("/add"); // Navigate to additem page
      // }, 1000);
    } else {
      toast.error(message || "Incorrect credentials. Please try again.");
    }
  } catch (error) {
    console.error("Login error:", error);
    toast.error(error.response?.data?.message || "An error occurred. Please try again.");
  }
};

  
  

  return (
    <div className='login-popup'>
      <form className='login-popup-container' onSubmit={currState === "Sign Up" ? handleRegisterSubmit : handleLoginSubmit}>
        <div className="login-popup-title">
          <p className='text-center'>{currState}</p>
          <img className='' onClick={() => setShowLogin(false)} src={assets.cross_icon} alt="close" />
        </div>
        <div className='login-popup-inputs'>
          {currState === "Login" ? null : (
          <input 
          name="name" // changed from "name" to "username"
          value={formData.name} 
          onChange={onChangeHandler} 
          className='' 
          type="text" 
          placeholder='Your Name' 
          required 
        />
         
          )}


          <input 
           name="emailId" 
           value={formData.emailId} 
           onChange={onChangeHandler} 
           className='' 
           type="email" 
           placeholder='Email' 
           required 
         />
          
          
          {currState === "Login" ? null : (
            <input 
              name="mobile" 
              value={formData.mobile} 
              onChange={onChangeHandler} 
              className='' 
              type="text" 
              placeholder='Mobile Number' 
              required 
            />
          )}

          <input 
            name="password" 
            value={formData.password} 
            onChange={onChangeHandler} 
            className='' 
            type="password" 
            placeholder='Password' 
            required 
          />
        </div>
        <button className=''>{currState === "Sign Up" ? "Create Account" : "Login"}</button>
        <div className="login-popup-condition">
          {/* <input type="checkbox" className="checkbox" />
          <p>Privacy Policy</p> */}
        </div>
        {currState === "Login" ? (
          <p>Don't have an account? <span className='' onClick={() => setCurrState("Sign Up")}>Sign up here</span></p>
        ) : (
          <p>Already have an account? <span className='' onClick={() => setCurrState("Login")}>Login here</span></p>
        )}
      </form>
    </div>
  );
};

export default Login;
