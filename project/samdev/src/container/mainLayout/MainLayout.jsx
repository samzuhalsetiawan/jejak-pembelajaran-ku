import React from "react";
import './MainLayout.css';
import AboutMe from "../aboutMe/AboutMe";
import WhatsNew from "../whatsNew/WhatsNew";
import Searching from "../../component/searching/Searching";

const MainLayout = () => {
  return (
    <div className='main-layout'>
      <div className='main-layout-child1'>
        <WhatsNew />
      </div>
      <div className='main-layout-child2'>
        <div className="main-layout-search">
          <Searching />
        </div>
        <AboutMe />
      </div>
    </div>
  )
}

export default MainLayout;