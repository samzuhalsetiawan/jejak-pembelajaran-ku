import React from "react";
import './WhatsNew.css';
import ContentBlogs from "../../component/contentBlogs/ContentBlogs";
import HeaderContent from "../../component/header/headerContent/HeaderContent";

const WhatsNew = () => {
  return (
    <div className='whats-new'>
      <HeaderContent headerName="What's New"/>
      <div className="whats-new-contents">
        <ContentBlogs judul='Judul' desc='ini adalah bagian keterangan, anggap aja panjang' />
        <ContentBlogs judul='Judul' desc='ini adalah bagian keterangan, anggap aja panjang' />
        <ContentBlogs judul='Judul' desc='ini adalah bagian keterangan, anggap aja panjang' />
        <ContentBlogs judul='Judul' desc='ini adalah bagian keterangan, anggap aja panjang' />
        <ContentBlogs judul='Judul' desc='ini adalah bagian keterangan, anggap aja panjang' />
        <ContentBlogs judul='Judul' desc='ini adalah bagian keterangan, anggap aja panjang' />
        <ContentBlogs judul='Judul' desc='ini adalah bagian keterangan, anggap aja panjang' />
        <ContentBlogs judul='Judul' desc='ini adalah bagian keterangan, anggap aja panjang' />
        <ContentBlogs judul='Judul' desc='ini adalah bagian keterangan, anggap aja panjang' />
      </div>
    </div>
  )
}

export default WhatsNew;