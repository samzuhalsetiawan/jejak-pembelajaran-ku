import React from "react";
import MainLayout from "../../container/mainLayout/MainLayout";
import Navbar from "../../container/navbar/Navbar";

const Home = () => {
  return (
    <div className='home-container'>
      <Navbar />
      <MainLayout />
     </div>
  )
}

export default Home;