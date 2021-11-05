import React, { Component } from "react";
import YoutubeComponent from "../component/YoutubeComponent/YoutubeComponent";

class Home extends Component {
  render() {
    return <div>
      <YoutubeComponent
        time='07:10'
        title='Tutorial 01'
        desc='By Sam Zuhal Setiawan'
      />
      <YoutubeComponent
        time='05:20'
        title='Tutorial 02'
        desc='By Sam Zuhal Setiawan'
      />
      <YoutubeComponent
        time='04:08'
        title='Tutorial 03'
        desc='By Sam Zuhal Setiawan'
      />
      <YoutubeComponent
        time='10:00'
        title='Tutorial 04'
        desc='By Sam Zuhal Setiawan'
      />
    </div>
  }
}

export default Home;