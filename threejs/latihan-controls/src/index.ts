import * as THREE from 'three'
import * as dat from 'dat.gui'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls'

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
//////////////////////////////////////////////////////////////////////////////////

const boxGeo = new THREE.BoxGeometry(0.5, 0.5, 0.5)
const colorMaterial = new THREE.MeshPhongMaterial({ color: 0x6495ED, shininess: 100 })
const box = new THREE.Mesh(boxGeo, colorMaterial)
box.translateX(-1)
scene.add(box)

const boxGeo2 = new THREE.BoxGeometry(0.5, 0.5, 0.5)
const colorMaterial2 = new THREE.MeshLambertMaterial({ color: 0x32CD32 })
const box2 = new THREE.Mesh(boxGeo2, colorMaterial2)
box2.translateX(1)
scene.add(box2)

const ambientLight = new THREE.AmbientLight(0x800080)
scene.add(ambientLight)

const pointLight = new THREE.PointLight(0xFF8C00)
pointLight.position.set(-1,1,3)
scene.add(pointLight)

const pointLightHelper = new THREE.PointLightHelper(pointLight, 0.3)
scene.add(pointLightHelper)

const gridhelper = new THREE.GridHelper(100, 100)
gridhelper.position.y = -0.48
scene.add(gridhelper)

const controls = new OrbitControls(camera, renderer.domElement)

const gui = new dat.GUI()
let selectedObject: THREE.Object3D
const guiData = {
  positionX: 0, positionY: 0, positionZ: 0,
  rotationX: 0, rotationY: 0, rotationZ: 0,
  scaleX: 1, scaleY: 1, scaleZ: 1
}

for (const key in guiData) {
  gui.add(guiData, key, -10.0, 10.0, 0.001).name(key)
}
const plControllerX = gui.add(pointLight.position, 'x', -10.0, 10.0, 0.001)
const plControllerY = gui.add(pointLight.position, 'y', -10.0, 10.0, 0.001)
const plControllerZ = gui.add(pointLight.position, 'z', -10.0, 10.0, 0.001)
const plControllerIntensity = gui.add(pointLight, 'intensity', -10.0, 10.0, 0.001)

const raycaster = new THREE.Raycaster()
const pointer = new THREE.Vector2()

function onPointerMove(event: PointerEvent) {
  pointer.x = ( event.clientX / window.innerWidth ) * 2 - 1;
  pointer.y = - ( event.clientY / window.innerHeight ) * 2 + 1;
}
function onPointerDown( event: PointerEvent ) {
  const intersect = raycaster.intersectObjects([box, box2])
  if (intersect[0]) {
    const gameObject = intersect[0].object
    const controllers = gui.__controllers
    controllers[0].setValue(gameObject.position.x)
    controllers[1].setValue(gameObject.position.y)
    controllers[2].setValue(gameObject.position.z)
    controllers[3].setValue(gameObject.rotation.x)
    controllers[4].setValue(gameObject.rotation.y)
    controllers[5].setValue(gameObject.rotation.z)
    controllers[6].setValue(gameObject.scale.x)
    controllers[7].setValue(gameObject.scale.y)
    controllers[8].setValue(gameObject.scale.z)
    selectedObject = gameObject
  }
}

window.addEventListener('pointerdown', onPointerDown);
window.addEventListener('pointermove', onPointerMove);


//////////////////////////////////////////////////////////////////////////////////
function update() {
  requestAnimationFrame(update)
  const deltaTime = clock.getDelta()
  controls.update()
  raycaster.setFromCamera(pointer, camera)

  if (selectedObject) {
    selectedObject.position.set(guiData.positionX, guiData.positionY, guiData.positionZ)
    selectedObject.rotation.set(guiData.rotationX, guiData.rotationY, guiData.rotationZ)
    selectedObject.scale.set(guiData.scaleX, guiData.scaleY, guiData.scaleZ)
  }

  pointLight.position.x = plControllerX.getValue()
  pointLight.position.y = plControllerY.getValue()
  pointLight.position.z = plControllerZ.getValue()
  pointLight.intensity = plControllerIntensity.getValue()

  renderer.render(scene, camera)
}

update()