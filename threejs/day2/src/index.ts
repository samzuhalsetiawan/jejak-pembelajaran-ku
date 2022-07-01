import { time } from 'console'
import * as THREE from 'three'

const scene = new THREE.Scene()
const camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 1000)
const renderer = new THREE.WebGLRenderer()
const clock = new THREE.Clock()

renderer.setSize(window.innerWidth, window.innerHeight)
document.body.appendChild(renderer.domElement)
camera.position.z = 5

const textureLoader = new THREE.TextureLoader()

const rotanTexture = textureLoader.load('../dist/img/rotan_wall.jpg')

const ambientLight = new THREE.AmbientLight(0x6495ED, 0.5)
scene.add(ambientLight)

const pointLight = new THREE.PointLight(0xFF8C00, 1)
pointLight.position.set(-0.5, 0.3, 4)
scene.add(pointLight)

const pointLightHelper = new THREE.PointLightHelper(pointLight, 0.2)
scene.add(pointLightHelper)

const boxGeo = new THREE.BoxGeometry()
// const boxMat = new THREE.MeshBasicMaterial({ map: rotanTexture })
const boxMat = new THREE.MeshPhongMaterial({
  map: rotanTexture,
  shininess: 100,
  bumpMap: rotanTexture,
  bumpScale: 0.1
})
const box =  new THREE.Mesh(boxGeo, boxMat)

scene.add(box)


window.onresize = () => {
  renderer.setSize(window.innerWidth, window.innerHeight)
  camera.aspect = window.innerWidth / window.innerHeight
  camera.updateProjectionMatrix()
}

let timer = 0;
function update() {
  requestAnimationFrame(update)
  const deltaTime = clock.getDelta()
  const speed = 0.2

  box.rotation.x += speed * deltaTime 
  box.rotation.y += speed * deltaTime 
  box.rotation.z += speed * deltaTime 

  renderer.render(scene, camera)
}

update()