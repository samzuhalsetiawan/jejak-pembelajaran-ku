import * as THREE from 'three'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import { DRACOLoader } from 'three/examples/jsm/loaders/DRACOLoader'

const scene = new THREE.Scene()
const camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 1000)
const renderer = new THREE.WebGLRenderer()
const clock = new THREE.Clock()

renderer.setSize(window.innerWidth, window.innerHeight)
renderer.setPixelRatio(window.devicePixelRatio)
document.body.appendChild(renderer.domElement)

camera.position.set(-3, 0.3, 4)

const controls = new OrbitControls(camera, renderer.domElement)
const gridHelper = new THREE.GridHelper(100, 100)
scene.add(gridHelper)

window.onresize = () => {
  renderer.setSize(window.innerWidth, window.innerHeight)
  camera.aspect = window.innerWidth / window.innerHeight
  camera.updateProjectionMatrix()
}

function getRandomNumberBetween(minInclusive: number, maxInclusive: number): number {
  return Math.floor(Math.random() * (maxInclusive - minInclusive + 1) + minInclusive)
}

let show = true

class Fireflies {
  constructor(
    id: number,
    originPos: THREE.Matrix4,
    xMovementFormula: (time: number) => number,
    yMovementFormula: (time: number) => number,
    zMovementFormula: (time: number) => number,
    firefliesX: number,
    firefliesY: number,
    firefliesZ: number
  ) {
    this.id = id
    this.originalPosition = originPos
    this.xMovementFormula = xMovementFormula
    this.yMovementFormula = yMovementFormula
    this.zMovementFormula = zMovementFormula
    this.firefliesX = firefliesX
    this.firefliesY = firefliesY
    this.firefliesZ = firefliesZ
  }
  public readonly id: number
  public readonly xMovementFormula: (time: number) => number
  public readonly yMovementFormula: (time: number) => number
  public readonly zMovementFormula: (time: number) => number
  public readonly originalPosition: THREE.Matrix4
  public readonly firefliesX: number
  public readonly firefliesY: number
  public readonly firefliesZ: number
  public getNewAnimatePosition(): THREE.Matrix4 {
    const time = Date.now() * 0.0005;
    const newPosX = this.xMovementFormula(time)
    const newPosY = this.yMovementFormula(time)
    const newPosZ = this.zMovementFormula(time)
    const translateMatrix = new THREE.Matrix4().makeTranslation(newPosX, newPosY, newPosZ)
    const newMatrix = this.originalPosition.clone().multiply(translateMatrix)
    return newMatrix
  }
}

const startPos1 = 1000
const startPos2 = 2500
const startPos3 = 4000
const startPos4 = 5500
const speed1 = 0.3
const speed2 = 0.5
const speed3 = 0.7
const maxHeightFlare = 5
const maxFlareSpeed = 0.5
const jumlahFlare = 100
const jumlahFireflies = 2000
const maxAreaFireflies = new THREE.Vector3(50, 2, 50)
const minAreaFireflies = new THREE.Vector3(-50, 0, -50)

const listWarna: THREE.Color[] = [
  new THREE.Color(0xff0040),
  new THREE.Color(0x0040ff),
  new THREE.Color(0x80ff80),
  new THREE.Color(0xffaa00)
]

const listFireFlies: Fireflies[] = []

const listFirefliesXMovementFormula: ((time: number) => number)[] = [
  time => Math.sin( time * speed3 + startPos4 ) * 0.8,
  time => Math.cos( time * speed1 + startPos3 ) * 0.8,
  time => Math.sin( time * speed3 + startPos2 ) * 0.8,
  time => Math.sin( time * speed1 + startPos1 ) * 0.8
]

const listFirefliesYMovementFormula: ((time: number) => number)[] = [
  time => (Math.cos( time * speed2 + startPos1 ) * 10 + 10) / 20,
  time => (Math.cos( time * speed2 + startPos2 ) * 10 + 10) / 20,
  time => (Math.cos( time * speed2 + startPos3 ) * 10 + 10) / 20,
  time => (Math.cos( time * speed2 + startPos4 ) * 10 + 10) / 20
]

const listFirefliesZMovementFormula: ((time: number) => number)[] = [
  time => Math.cos( time * speed1 + startPos2 ) * 0.8,
  time => Math.sin( time * speed3 + startPos4 ) * 0.8,
  time => Math.sin( time * speed1 + startPos1 ) * 0.8,
  time => Math.sin( time * speed3 + startPos2 ) * 0.8
] 

// HESMISPHERE LIGHT
// const hemisphereLight = new THREE.HemisphereLight(0xEAF6F6, 0x3EC70B)
// const hemisphereLightHelper = new THREE.HemisphereLightHelper(hemisphereLight, 0.5)
// hemisphereLight.position.y = 5
// scene.add(hemisphereLight, hemisphereLightHelper)

const ambientLight = new THREE.AmbientLight(0x5B4B8A)
scene.add(ambientLight)

const gltfLoader = new GLTFLoader()
const textureLoader = new THREE.TextureLoader()
const dracoLoader = new DRACOLoader()
dracoLoader.setDecoderPath('../node_modules/three/examples/js/libs/draco')
gltfLoader.setDRACOLoader(dracoLoader)

const firefliesGeo = new THREE.SphereBufferGeometry(0.03)

// Merah
const light1 = new THREE.PointLight( 0xff0040, 1, 50 );
light1.add( new THREE.Mesh( firefliesGeo, new THREE.MeshBasicMaterial( { color: 0xff0040 } ) ) );

// Biru
const light2 = new THREE.PointLight( 0x0040ff, 1, 50 );
light2.add( new THREE.Mesh( firefliesGeo, new THREE.MeshBasicMaterial( { color: 0x0040ff } ) ) );

// Hijau
const light3 = new THREE.PointLight( 0x80ff80, 1, 50 );
light3.add( new THREE.Mesh( firefliesGeo, new THREE.MeshBasicMaterial( { color: 0x80ff80 } ) ) );

// Orange
const light4 = new THREE.PointLight( 0xffaa00, 1, 50 );
light4.add( new THREE.Mesh( firefliesGeo, new THREE.MeshBasicMaterial( { color: 0xffaa00 } ) ) );

scene.add(light1, light2, light3, light4)


const firefliesMaterial = new THREE.MeshBasicMaterial()
const fireflies = new THREE.InstancedMesh(firefliesGeo, firefliesMaterial, jumlahFireflies)
for (let i = 0; i < jumlahFireflies; i++) {
  const matrix = new THREE.Matrix4()
  const firefliesX = getRandomNumberBetween(minAreaFireflies.x, maxAreaFireflies.x) 
  const firefliesY = getRandomNumberBetween(minAreaFireflies.y, maxAreaFireflies.y) 
  const firefliesZ = getRandomNumberBetween(minAreaFireflies.z, maxAreaFireflies.z)
  const firefliesMovX = listFirefliesXMovementFormula[getRandomNumberBetween(0, listFirefliesXMovementFormula.length-1)]
  const firefliesMovY = listFirefliesYMovementFormula[getRandomNumberBetween(0, listFirefliesYMovementFormula.length-1)]
  const firefliesMovZ = listFirefliesZMovementFormula[getRandomNumberBetween(0, listFirefliesZMovementFormula.length-1)]
  const firefliesColor = listWarna[getRandomNumberBetween(0, listWarna.length-1)]
  matrix.setPosition(firefliesX, firefliesY, firefliesZ)
  fireflies.setMatrixAt(i, matrix)
  fireflies.setColorAt(i, firefliesColor)
  listFireFlies.push(new Fireflies(i, matrix, firefliesMovX, firefliesMovY, firefliesMovZ, firefliesX, firefliesY, firefliesZ))
}
scene.add(fireflies)

let bungaMesh: THREE.Object3D | undefined;

gltfLoader.load('../bunga.glb', gltf => {
  bungaMesh = gltf.scene.children.find(object3d => object3d.name == 'Batang')
  if (bungaMesh) {
    scene.add(bungaMesh)
  }
})
interface Flare {
  position: THREE.Vector3,
  velocity: THREE.Vector3
}
const kump_flare: Flare[] = []
let particle: THREE.Points

function initFlare(materialParam: THREE.PointsMaterialParameters) {
  const particleGeo = new THREE.BufferGeometry()
  const flareMaterial = new THREE.PointsMaterial(materialParam)
  
  for(let i = 0; i < jumlahFlare/2; i += 0.4) {
    for(let j = 0; j < jumlahFlare/2; j += 0.4) {
      const flare: Flare = {
        position: new THREE.Vector3(i, 0, j),
        velocity: new THREE.Vector3(0, Math.random() * maxFlareSpeed, 0)
      }
      kump_flare.push(flare)
    }
  }
  particleGeo.setAttribute('position', new THREE.BufferAttribute(
    new Float32Array(kump_flare.map(({ position }) => [position.x, position.y, position.z]).flat()), 3
  ))
  particle = new THREE.Points(particleGeo, flareMaterial)
  particle.position.set(-jumlahFlare/4, 0, -jumlahFlare/4)
  scene.add(particle)
}

textureLoader.load('../dist/flare_yellow1.png', texture => {
  initFlare({ size: 0.5, map: texture, transparent: true, depthTest: false })
})


function update() {
  requestAnimationFrame(update)
  const deltaTime = clock.getDelta();
  const time = Date.now() * 0.0005;

  for (const ff of listFireFlies) {
    const newPositionMatrix = ff.getNewAnimatePosition()
    fireflies.setMatrixAt(ff.id, newPositionMatrix)
  }

  // const matrixF1 = new THREE.Matrix4()
  // const xf1 = Math.sin( time * speed3 + startPos4 ) * 0.8
  // const yf1 = (Math.cos( time * speed2 + startPos1 ) * 10 + 10) / 20
  // const zf1 = Math.cos( time * speed1 + startPos2 ) * 0.8
  // matrixF1.setPosition(xf1, yf1, zf1)
  // fireflies.setMatrixAt(0, matrixF1)

  // const matrixF2 = new THREE.Matrix4()
  // const xf2 = Math.cos( time * speed1 + startPos3 ) * 0.8
  // const yf2 = (Math.cos( time * speed2 + startPos2 ) * 10 + 10) / 20
  // const zf2 = Math.sin( time * speed3 + startPos4 ) * 0.8
  // matrixF2.setPosition(xf2, yf2, zf2)
  // fireflies.setMatrixAt(1, matrixF2)

  // const matrixF3 = new THREE.Matrix4()
  // const xf3 = Math.sin( time * speed3 + startPos2 ) * 0.8
  // const yf3 = (Math.cos( time * speed2 + startPos3 ) * 10 + 10) / 20
  // const zf3 = Math.sin( time * speed1 + startPos1 ) * 0.8
  // matrixF3.setPosition(xf3, yf3, zf3)
  // fireflies.setMatrixAt(2, matrixF3)

  // const matrixF4 = new THREE.Matrix4()
  // const xf4 = Math.sin( time * speed1 + startPos1 ) * 0.8
  // const yf4 = (Math.cos( time * speed2 + startPos4 ) * 10 + 10) / 20
  // const zf4 = Math.sin( time * speed3 + startPos2 ) * 0.8
  // matrixF4.setPosition(xf4, yf4, zf4)
  // fireflies.setMatrixAt(3, matrixF4)

  fireflies.instanceMatrix.needsUpdate = true

  light1.position.x = Math.sin( time * speed3 + startPos4 ) * 0.8;
  light1.position.y = (Math.cos( time * speed2 + startPos1 ) * 10 + 10) / 20;
  light1.position.z = Math.cos( time * speed1 + startPos2 ) * 0.8;

  light2.position.x = Math.cos( time * speed1 + startPos3 ) * 0.8;
  light2.position.y = (Math.cos( time * speed2 + startPos2 ) * 10 + 10) / 20;
  light2.position.z = Math.sin( time * speed3  + startPos4 ) * 0.8;

  light3.position.x = Math.sin( time * speed3 + startPos2 ) * 0.8;
  light3.position.y = (Math.cos( time * speed2 + startPos3 ) * 10 + 10) / 20;
  light3.position.z = Math.sin( time * speed1 + startPos1 ) * 0.8;

  light4.position.x = Math.sin( time * speed1 + startPos1 ) * 0.8;
  light4.position.y = (Math.cos( time * speed2 + startPos4 ) * 10 + 10) / 20;
  light4.position.z = Math.sin( time * speed3 + startPos2 ) * 0.8;

  if (particle) {
    const flarePositions = particle.geometry.getAttribute('position')
    for (let i = 0; i < flarePositions.count; i++) {
      const flareYPosition = flarePositions.getY(i)
      if (flareYPosition > maxHeightFlare) {
        flarePositions.setY(i, 0)
      } else {
        flarePositions.setY(i, flareYPosition + kump_flare[i].velocity.y * deltaTime)
      }
    }
    particle.geometry.getAttribute('position').needsUpdate = true
  }

  renderer.render(scene, camera)
}

update()