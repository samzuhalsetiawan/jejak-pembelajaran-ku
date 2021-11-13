import React from "react";
import { GlobalConsumer } from "../../context/context";

function Home(props) {
  return (
    <>
      <h1>Ini halaman Home</h1>
      <input className="home-counter-display" type="number" readOnly value={props.count}/>
      <input type="text" readOnly value={props.count2.counter}/>
      <button className='home-btn-tambah' onClick={() => {props.dispatch({type: "TAMBAH2"})}}>Tambah2</button>
      <button className='home-btn-tambah' onClick={() => {props.dispatch({type: "TAMBAH"})}}>Tambah</button>
      <button className='home-btn-kurang' onClick={() => {props.dispatch({type: "KURANG"})}}>Kurang</button>
    </>  
  )
}

export default GlobalConsumer(Home);