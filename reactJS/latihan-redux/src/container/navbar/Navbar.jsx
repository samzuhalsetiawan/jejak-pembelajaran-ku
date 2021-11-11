import React, {Component} from "react";
import { Link } from 'react-router-dom';
import './Navbar.css';

class Navbar extends Component {
    render() {
        return <>
            <div className='navbar-container'>
                <Link to='/'><button>Home</button></Link>
                <Link to='/about'><button>About</button></Link>
                <Link to='/Product'><button>Product</button></Link>
            </div>
        </>
    }
}

export default Navbar;