import React from "react";
import './YoutubeComponent.css'

const YoutubeComponent = (props) => {
  return <div className='container'>
    <div className='video'>
      <span className='time'>{props.time}</span>
    </div>
    <p className='title'>{props.title}</p>
    <p className='desc'>{props.desc}</p>
  </div>
}

YoutubeComponent.defaultProps = {
  'time': '00:00',
  'title': 'Your Title Here',
  'desc': 'Your Description Here'
}

export default YoutubeComponent;