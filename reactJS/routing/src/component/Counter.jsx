import React from "react";
import './Counter.css';

function Counter(props) {
  return <div className='CounterContainer'>
    <div onClick={props.onUp}><span className='up'></span></div>
    <div className='CounterDisplay'>{props.nilai}</div>
    <div onClick={props.onDown}><span className='down'></span></div>
  </div>
}

export default Counter;