var arr = [];
var tinggi = parseInt(prompt("Masukan tingkat bilangan pascal yg kamu inginkan! (dalam angka):"));
var kunci = 0;
function tambah (a,b) {
	return a + b;
}

if (tinggi > 1) {
	arr.push(1);
	console.log(arr.join(" "));
}
if (tinggi > 2) {
	arr.push (1);
	console.log(arr.join(" "));
}
if (tinggi > 3) {
	for (lantai = 3; lantai <= tinggi; lantai++) {
		if (lantai % 2 != 0) {
			kunci++;
		}
		arr.push(1);
		for (opr = 1; opr <= kunci; opr++) {
			arr.push(tambah(arr[opr-1],arr[opr]));
		}
		for (hapus = 1; hapus < lantai; hapus++) {
			arr.shift();
		}
		if (lantai % 2 != 0) {
			for (dataGanjil = 1; dataGanjil <= kunci; dataGanjil++) {
				arr.push(arr[kunci - dataGanjil]);
			}
		}else{
			for (dataGenap = 1; dataGenap <= (kunci + 1); dataGenap++) {
				arr.push(arr[(kunci+1) - dataGenap]);
			}
		}
		console.log(arr.join(" "));
	}
}