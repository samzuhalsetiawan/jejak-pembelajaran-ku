var diag = parseInt(prompt("masukan tinggi belah ketupat! (dalam angka): "));
var s = "";
var multi = -1;
function ganGil (a) {
	if (a % 2 == 0) {
		return a+1;
	}else{return a;}
}
var ver = ganGil(diag);
var sisi = (ver / 2);

for (tinggi = 1; tinggi <= ver; tinggi++) {
	if (tinggi <= (sisi+1)) {
		multi += 2;
		for (kamar = 1; kamar <= ((sisi+1)-tinggi); kamar++) {
			s += " ";
		}
		for (bintangAtas = 1; bintangAtas <= multi; bintangAtas++) {
			s += "*";
		}
	}else{
		multi -= 2;
		for (kamarBawah = (sisi+1); kamarBawah <= tinggi; kamarBawah++) {
			s += " ";
		}
		for (bintangBawah = 1; bintangBawah <= multi; bintangBawah++) {
			s += "*";
		}
	}
	s += "\n";
}
console.log(s);
console.log("by:SamZ");