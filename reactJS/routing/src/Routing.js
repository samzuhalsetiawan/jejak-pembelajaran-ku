import React, { Component } from "react";
import { Routes, Route, Link } from 'react-router-dom';
import Home from "./container/Home";
import About from "./container/About";
import Page from "./container/Page";
import Counter from "./component/Counter";

export default class Routing extends Component {

  state = {
    nilai: 0
  }

  onUp = () => {
    this.setState({
      nilai: this.state.nilai + 1
    });
  }

  onDown = () => {
    if (!this.state.nilai) return
    this.setState({
      nilai: this.state.nilai - 1
    });
  }

  render() {
    return <>
      <ul>
        <li>
          <Link to='/'>Home</Link>
        </li>
        <li>
          <Link to='/about'>About</Link>
        </li>
        <li>
          <Link to={`/page/${this.state.nilai}`}>Page</Link>
        </li>
      </ul>
      <Counter onUp={this.onUp} onDown={this.onDown} nilai={this.state.nilai} />
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/about' element={<About />} />
        <Route path='/page/:id' element={<Page />} />
      </Routes>
    </>
  }
}