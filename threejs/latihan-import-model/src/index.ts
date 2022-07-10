import * as THREE from 'three'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import { DRACOLoader } from 'three/examples/jsm/loaders/DRACOLoader'

const scene = new THREE.Scene()
const camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 1000)
const renderer = new THREE.WebGLRenderer()
const clock = new THREE.Clock()

renderer.setSize(window.innerWidth, window.innerHeight)
document.body.appendChild(renderer.domElement)

camera.position.z = 5

window.onresize = () => {
  renderer.setSize(window.innerWidth, window.innerHeight)
  camera.aspect = window.innerWidth / window.innerHeight
  camera.updateProjectionMatrix()
}

const gridHelper = new THREE.GridHelper(100, 100)
scene.add(gridHelper)

const orbitControls = new OrbitControls(camera, renderer.domElement)

const ambientLight = new THREE.AmbientLight()
scene.add(ambientLight)

const loader = new GLTFLoader()

const dracoLoader = new DRACOLoader()
dracoLoader.setDecoderPath('../node_modules/three/examples/js/libs/draco')
loader.setDRACOLoader(dracoLoader)

loader.load('../donat_biru.gltf', gltf => {
  console.log(gltf)
  scene.add(gltf.scene)
})

function update() {
  requestAnimationFrame(update)
  const deltaTime = clock.getDelta()

  renderer.render(scene, camera)
}

update()