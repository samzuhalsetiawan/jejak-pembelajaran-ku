const pangkat = Module.cwrap("pangkat", "number", ["number", "number"])

const hasil = pangkat(2, 3)
console.log(hasil)