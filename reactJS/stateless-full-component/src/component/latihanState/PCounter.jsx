import React, { Component } from "react";
import Counter from "./Counter";

class PCounter extends Component {

  state = {
    count: 0
  }

  incCount = () => {
    this.setState({
      count: this.state.count + 1
    });
  }

  decCount = () => {
    if (!this.state.count) return;
    this.setState({
      count: this.state.count - 1
    });
  }

  render() {
    return <div>
      <div className='containerPCounter'>
        <div className='counterDisplay'> {this.state.count} </div>
        <Counter incCount={this.incCount} decCount={this.decCount} nilai={this.state.count} />
      </div>
    </div>
  }
}

export default PCounter;