import * as THREE from 'three'

export function latihanBasic1() {
  const scene = new THREE.Scene()
  const camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 1000)
  const renderer = new THREE.WebGLRenderer()
  const clock = new THREE.Clock()

  renderer.setSize(window.innerWidth, window.innerHeight)
  document.body.appendChild(renderer.domElement)

  const boxGeo = new THREE.BoxGeometry(1,1,1)
  const boxMat = new THREE.MeshBasicMaterial({ color: 0xff0000 })

  const boxGeoBuffer = new THREE.BufferGeometry()
  const vertices = new Float32Array([
    -1.0, 1.0, 0.0, //0
    -1.0, -1.0, 0.0, //1
    1.0, -1.0, 0.0, //2
    1.0, 1.0, 0.0, //3
    1.0, 1.0, -2.0, //4
    1.0, -1.0, -2.0, //5
    -1.0, 1.0, -2.0, //6
    -1.0, -1.0, -2.0 //7
  ])
  const verticesIndexed = [
    0,1,2,
    0,2,3,
    3,2,4,
    2,5,4,
    0,6,7,
    1,0,7,
    6,4,5,
    6,5,7,
    0,3,6,
    3,4,6,
    1,7,2,
    2,7,5
  ]
  const verticesColors = new Float32Array([
    1.0, 0.0, 0.0,
    1.0, 0.0, 0.0,
    1.0, 1.0, 0.0,
    1.0, 1.0, 0.0,
    0.0, 1.0, 0.0,
    0.0, 1.0, 0.0,
    0.0, 0.0, 1.0,
    0.0, 0.0, 1.0
  ])
  boxGeoBuffer.setAttribute('position', new THREE.Float32BufferAttribute(vertices, 3))
  boxGeoBuffer.setAttribute('color', new THREE.Float32BufferAttribute(verticesColors, 3))
  const vertexColorMat = new THREE.MeshBasicMaterial({ vertexColors: true })
  boxGeoBuffer.setIndex(verticesIndexed)

  const vertices2 = new Float32Array([
    -1.0, 1.0, 0.0, //0
    -1.0, 1.0, 0.0, //1
    -1.0, -1.0, 0.0, //2
    -1.0, -1.0, 0.0, //3
    1.0, -1.0, 0.0, //4
    1.0, -1.0, 0.0, //5
    1.0, 1.0, 0.0, //6
    1.0, 1.0, 0.0, //7
    1.0, 1.0, -2.0, //8
    1.0, 1.0, -2.0, //9
    1.0, -1.0, -2.0, //10
    1.0, -1.0, -2.0, //11
    -1.0, 1.0, -2.0, //12
    -1.0, 1.0, -2.0, //13
    -1.0, -1.0, -2.0, //14
    -1.0, -1.0, -2.0, //15
    -1.0, 1.0, -2.0, //16
    -1.0, 1.0, 0.0, //17
    1.0, 1.0, 0.0, //18
    1.0, 1.0, -2.0, //19
    -1.0, -1.0, 0.0, //20
    -1.0, -1.0, -2.0, //21
    1.0, -1.0, -2.0, //22
    1.0, -1.0, 0.0 //23
  ])
  const verticesIndexed2 = [
    //depan
    0,2,4,
    4,6,0,
    //kanan
    7,5,11,
    11,9,7,
    //kiri
    3,1,13,
    13,15,3,
    //belakang
    12,8,10,
    10,14,12,
    //atas
    16,17,18,
    18,19,16,
    //bawah
    20,21,22,
    22,23,20
  ]
  const verticesColors2 = new Float32Array([
    1.0, 0.0, 0.0, //0
    0.0, 1.0, 0.0, //1
    1.0, 0.0, 0.0, //2
    0.0, 1.0, 0.0, //3
    1.0, 0.0, 0.0, //4
    1.0, 1.0, 0.0, //5
    1.0, 0.0, 0.0, //6
    1.0, 1.0, 0.0, //7
    0.0, 1.0, 1.0, //8
    1.0, 1.0, 0.0, //9
    0.0, 1.0, 1.0, //10
    1.0, 1.0, 0.0, //11
    0.0, 1.0, 1.0, //12
    0.0, 1.0, 0.0, //13
    0.0, 1.0, 1.0, //14
    0.0, 1.0, 0.0, //15
    0.0, 0.0, 1.0, //16
    0.0, 0.0, 1.0, //17
    0.0, 0.0, 1.0, //18
    0.0, 0.0, 1.0, //19
    1.0, 0.0, 1.0, //20
    1.0, 0.0, 1.0, //21
    1.0, 0.0, 1.0, //22
    1.0, 0.0, 1.0 //23
  ])

  const boxGeoBuffer2 = new THREE.BufferGeometry()
  boxGeoBuffer2.setAttribute('position', new THREE.Float32BufferAttribute(vertices2, 3))
  boxGeoBuffer2.setIndex(verticesIndexed2)
  boxGeoBuffer2.setAttribute('color', new THREE.Float32BufferAttribute(verticesColors2, 3))

  const boxMesh = new THREE.Mesh(boxGeoBuffer, vertexColorMat)
  const boxMesh2 = new THREE.Mesh(boxGeo, boxMat)
  const boxMesh3 = new THREE.Mesh(boxGeoBuffer2, vertexColorMat)
  boxMesh.scale.set(0.5, 0.5, 0.5)
  boxMesh3.scale.set(0.5, 0.5, 0.5)
  boxMesh.position.y = -1
  boxMesh2.position.set(1,1,0)
  boxMesh3.position.set(-1, 1, 0)
  scene.add(boxMesh2)
  scene.add(boxMesh)
  scene.add(boxMesh3)
  camera.position.z = 5

  window.onresize = () => {
    renderer.setSize(window.innerWidth, window.innerHeight)
    camera.aspect = window.innerWidth / window.innerHeight
    camera.updateProjectionMatrix()
  }

  function animate() {
    requestAnimationFrame(animate)
    const speed = 1
    const deltaTime = clock.getDelta()

    boxMesh.rotation.x += speed * deltaTime
    boxMesh.rotation.y += speed * deltaTime
    boxMesh.rotation.z += speed * deltaTime

    boxMesh2.rotation.x += speed * deltaTime
    boxMesh2.rotation.y += speed * deltaTime
    boxMesh2.rotation.z += speed * deltaTime
    
    boxMesh3.rotation.x += speed * deltaTime
    boxMesh3.rotation.y += speed * deltaTime
    boxMesh3.rotation.z += speed * deltaTime

    renderer.render(scene, camera)
  }



  animate()
}