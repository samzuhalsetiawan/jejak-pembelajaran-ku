import * as THREE from 'three'
import WebGL from './WebGL/WebGL'
import { latihanBasic1 } from './LatihanBasic1/latihanBasic1';
import { latihanDrawLine } from './LatihanDrawLine/latihanDrawLine';

if (!WebGL.isWebGLAvailable()) {
  const warning = WebGL.getWebGLErrorMessage();
	document.getElementById( 'container' ).appendChild( warning );
  throw new Error("Your Device Doesn't Support WebGL")
}

// latihanBasic1()
latihanDrawLine()