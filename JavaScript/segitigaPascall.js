const tinggiSegitiga = 5; //tentukan berapa tinggi segitiga
let lantaiAtas = [1];
let lantaiBawah = [];

for (let i = 0; i < tinggiSegitiga; i++) {
  
  // menentukan nilai lantai bawah berdasarkan nilai lantai atas
  for (let j = 0; j <= i; j++) {
    (j == 0 || j == i) ?
      lantaiBawah[j] = 1 :
      lantaiBawah[j] = lantaiAtas[j-1] + lantaiAtas[j];
  }

  // menampilkan dilayar
  console.log(" ".repeat(tinggiSegitiga-i) + lantaiBawah.join(" "));

  // tukar dan reset
  lantaiAtas = lantaiBawah;
  lantaiBawah = [];
}
