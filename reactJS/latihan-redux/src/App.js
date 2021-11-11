import './App.css';
import { Routes, Route } from 'react-router-dom';
import Home from './container/home/Home';
import About from './container/about/About';
import Product from './container/product/Product';
import Navbar from './container/navbar/Navbar';

function App() {
  return ( <>
    <Navbar />
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/about" element={<About />} />
      <Route path="/product" element={<Product />} />
    </Routes>
  </>
  );
}

export default App;
