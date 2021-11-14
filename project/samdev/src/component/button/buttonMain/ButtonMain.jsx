import React from "react";
import './ButtonMain.css';

const ButtonMain = (props) => {
  return (
    <button className="button-main">{props.buttonName}</button>
  )
}

export default ButtonMain;