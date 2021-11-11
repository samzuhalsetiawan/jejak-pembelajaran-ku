import React, {Component} from 'react';
import { connect } from 'react-redux';

class About extends Component {
    render() {
        return <>
            <h1>Ini About</h1>
            <input type="number" readOnly value={this.props.counter}/>
        </>
    }
}

const mapStateToProps = (state) => {
    return {
        counter: state.counter
    }
}

export default connect(mapStateToProps)(About);