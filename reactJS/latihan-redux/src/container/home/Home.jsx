import React, {Component} from 'react';
import { connect } from 'react-redux';

class Home extends Component {
    render() {
        return <>
            <h1>Welcome Costumer 😘</h1>
            <input type="number" readOnly value={this.props.counter}/>
            <button className='home-btn-tambah' onClick={this.props.TAMBAH_COUNTER}>Tambah</button>
            <button className='home-btn-kurang' onClick={this.props.KURANG_COUNTER}>Kurang</button>
        </>
    }
}

const mapStateToProps = (state) => {
    return {
        counter: state.counter
    }
}
const mapDispatchToProps = (dispatch) => {
    return {
        TAMBAH_COUNTER: () => dispatch({type: "TAMBAH_COUNTER"}),
        KURANG_COUNTER: () => dispatch({type: "KURANG_COUNTER"})
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Home);