import React from 'react';
import { Route, Routes, Link } from 'react-router-dom';
import About from './component/about/About';
import Home from './component/home/Home';
import {GlobalProvider} from './context/context';

function App() {
  return (
    <>
      <Link to="/" >Home </Link>
      <Link to="/about" >About </Link>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
      </Routes>
    </>
  );
}

export default GlobalProvider(App);
