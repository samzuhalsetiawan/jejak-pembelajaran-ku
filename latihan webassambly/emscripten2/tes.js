async function init() {
  const wasm = await WebAssembly.instantiateStreaming(fetch("./hello.wasm"), {
    env: {},
    wasi_snapshot_preview1: {}
  })
  const module = WebAssembly.Module.exports(wasm.module)
  window.module = module
  console.log(module)
}

console.log("START")
init()