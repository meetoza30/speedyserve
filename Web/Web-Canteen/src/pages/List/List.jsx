import React, { useEffect, useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import "./List.css";
import { assets } from "../../assets/assets";

const List = () => {

  const [list, setList] = useState([]);

  

  // Function to fetch food list
  const fetchList = async () => {
    try {
      const response = await axios.get("http://localhost:3000/api/food/list");
      if (response.data.success) {
        setList(response.data.data);
      } else {
        toast.error("Failed to fetch food items");
      }
    } catch (error) {
      console.error("Error fetching food items:", error);
      toast.error("Something went wrong. Please try again.");
    }
  };

  

  const removeFood = async (foodId) => {
    try {
        // Show confirmation before deletion
        const isConfirmed = window.confirm("Are you sure you want to remove this food item?");
        if (!isConfirmed) return;

        // Make the request to the backend to remove the food item
        const response = await axios.post('http://localhost:3000/api/food/delete', { id: foodId });

        // Check the response from the server
        if (response.data.success) {
            toast.success(response.data.message);
            // After removal, re-fetch the food list to update the UI
            fetchList();
        } else {
            toast.error(response.data.message || "Error removing food item");
        }
    } catch (error) {
        toast.error("Something went wrong. Please try again.");
        console.error(error);
    }
}


  useEffect(() => {
    fetchList();
  }, [])

  return (
    <div className='list add flex-col'>
      <p>All Foods List</p>
      <div className='list-table'>
        <div className="list-table-format title">
          <b>Image</b>
          <b>Name</b>
          <b>Estimate Time</b>
          <b>Price</b>
          <b>Action</b>
        </div>
        {list.map((item, index) => {
          return (
            <div key={index} className='list-table-format'>
              <img src={assets.food} alt="" />
              <p>{item.name}</p>
              <p>{item.estimateTime}(min)</p>
              <p>{item.price}</p>
              <p className='cursor' onClick={() => removeFood(item._id)}>x</p>

            </div>
          )
        })}
      </div>
    </div>
  )
}

export default List