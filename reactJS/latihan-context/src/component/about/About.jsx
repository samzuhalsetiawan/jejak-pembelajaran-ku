import React from "react";
import { GlobalConsumer } from "../../context/context";

const About = (props) => {
  return (
    <>
      <h1>Ini Halaman About</h1>
      <input type="text" readOnly value={props.count} />
    </>
  )
}

export default GlobalConsumer(About);