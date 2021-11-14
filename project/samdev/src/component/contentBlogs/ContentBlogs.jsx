import React from "react";
import './ContentBlogs.css';

const ContentBlogs = (props) => {
  return (
    <div className="content-blogs">
    <div className="content-blogs-preview"></div>
    <div className="content-blogs-desc">
      <h3 className="content-blogs-judul">{props.judul}</h3>
      <p className="content-blogs-keterangan">{props.desc}</p>
    </div>
  </div>
  )
}

export default ContentBlogs;