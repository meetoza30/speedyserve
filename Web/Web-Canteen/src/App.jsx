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
import Login from './components/Login/Login'
import { useState } from 'react'
import SpeedyServeLanding from './pages/Landing/Landing'

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
          <Route path="/add" element={<Add />} />
          <Route path="/list" element={<List />} />
          <Route path="/orders" element={<Orders />} />
        </Routes>
        
      </div>
      <Footer/>
    </div>
    </>
   
  )
}

export default App