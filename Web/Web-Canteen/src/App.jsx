import React from 'react'
import Navbar from './components/Navbar/Navbar'
import Sidebar from './components/Sidebar/Sidebar'
import { Route, Routes } from 'react-router-dom'
import Add from './pages/Add/Add'
import List from './pages/List/List'
import Orders from './pages/Orders/Orders'
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Footer from './components/Footer/Footer'
import { useState } from 'react'
import SpeedyServeLanding from './pages/Landing/Landing'
import Signup from './pages/Login/Signup'
import Login from './pages/Login/Login'
import CanteenDashboard from './pages/Dashboard/Dashboard'
import ManageMenu from './pages/Add/Add'
import CanteenProfile from './pages/Profile/Profile'

const App = () => {
  const[showLogin,setShowLogin]=useState(false);
  return (
    <>
    {showLogin?<Login setShowLogin={setShowLogin}/>:<></>  
    }
     <div className='app'>
      <ToastContainer />
      {/* <Navbar setShowLogin={setShowLogin}/> */}
      <hr />
      <div className="app-content">
        {/* <Sidebar /> */}
        <Routes>
          <Route path='/' element={<SpeedyServeLanding />}/>
          <Route path='/login' element={<Login />}/>
          <Route path='/signup' element={<Signup />}/>
          <Route path="/manage-menu" element={<ManageMenu />} />
          <Route path="/dashboard" element={<CanteenDashboard />} />
          <Route path="/orders" element={<Orders />} />
          <Route path='/profile' element = {<CanteenProfile />} />
        </Routes>
        
      </div>
      <Footer/>
    </div>
    </>
   
  )
}

export default App