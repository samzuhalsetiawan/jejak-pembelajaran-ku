import React from "react";
import HeaderContent from "../../component/header/headerContent/HeaderContent";
import './AboutMe.css';

const AboutMe = () => {
  return (
    <div className="about-me">
      <HeaderContent headerName='About Me' />
      <div className="about-me-foto-dan-keterangan">
        <div className="about-me-foto"></div>
        <div className='about-me-keterangan'>
          Hi there! 👋 <br />
          I'm Sam Zuhal Setiawan <br /><br />
          Currently i'm a collage student at <a href="https://unmul.ac.id">Mulawarman University</a><br /><br />
          I realy ❤️ learn <strong>Coding</strong> and <strong>Technologies</strong>
        </div>
      </div>
    </div>
  )
}

export default AboutMe;