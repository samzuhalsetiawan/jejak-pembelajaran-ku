import * as THREE from 'three'

export function latihanDrawLine() {

  const scene = new THREE.Scene()
  const camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 1, 500)
  const renderer = new THREE.WebGLRenderer()
  const clock = new THREE.Clock()

  renderer.setSize(window.innerWidth, window.innerHeight)
  document.body.appendChild(renderer.domElement)

  camera.position.set(0, 0, 100)
  camera.lookAt(0, 0, 0)


  const lineMaterial = new THREE.LineBasicMaterial({ color: 0x0000ff })
  const points = []
  points.push(new THREE.Vector3(-10, 0, 0))
  points.push(new THREE.Vector3( 0, 10, 0))
  points.push(new THREE.Vector3( 10, 0, 0))
  const lineGeometry = new THREE.BufferGeometry().setFromPoints(points)
  const line = new THREE.Line(lineGeometry, lineMaterial)
  scene.add(line)

  window.onresize = () => {
    renderer.setSize(window.innerWidth, window.innerHeight)
    camera.aspect = window.innerWidth / window.innerHeight
    camera.updateProjectionMatrix()
  }

  function update() {
    requestAnimationFrame(update)
    const deltaTime = clock.getDelta()

    line.rotation.y += 1 * deltaTime

    renderer.render(scene, camera)
  }

  update()
}