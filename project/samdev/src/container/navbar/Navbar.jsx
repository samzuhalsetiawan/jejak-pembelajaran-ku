import React from "react";
import ButtonMain from "../../component/button/buttonMain/ButtonMain";
import './Navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-brand">SamDev</div>
      <div className='navbar-btn-list'>
        <ButtonMain buttonName='Home' />
        <ButtonMain buttonName='About' />
        <ButtonMain buttonName='Template' />
      </div>
    </nav>
  )
}

export default Navbar;