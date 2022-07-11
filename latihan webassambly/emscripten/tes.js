let wasmExports = null

const wasmMemory = new WebAssembly.Memory({ initial: 256, maximum: 256 })
const wasmTable = new WebAssembly.Table({
  'initial': 1,
  'maximum': 1,
  'element': 'anyfunc'
})

async function loadWasm() {
  const response = await fetch("hello.wasm")
  const bytes = await response.arrayBuffer()
  const wasmObj = await WebAssembly.instantiate(bytes, info)
  wasmExports = wasmObj.instance.exports
}