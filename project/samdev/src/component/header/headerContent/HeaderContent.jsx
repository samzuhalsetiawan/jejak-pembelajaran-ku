import React from "react";
import './HeaderContent.css';

const HeaderContent = (props) => {
  return <div className="header-content">{props.headerName}</div>
}

export default HeaderContent;