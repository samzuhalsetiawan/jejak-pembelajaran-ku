import React from "react";
import './Counter.css';

const Counter = (props) => {

  const minusClicked = () => {
    props.decCount();
  }

  const plusClicked = () => {
    props.incCount();
  }

  return <div>
    <div className='containerCounter'>
      <div className='plus' onClick={plusClicked}> + </div>
      <div className='value'> {props.nilai} </div>
      <div className='minus' onClick={minusClicked}> &minus; </div>
    </div>
  </div>
}

export default Counter;