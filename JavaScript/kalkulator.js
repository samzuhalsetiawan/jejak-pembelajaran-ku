const selectContainer = document.getElementsByClassName('container');
const selectLayar = document.querySelector('.layar h1');
let angkaMem = [];
let opMem = [];
const angkaInput = ["1","2","3","4","5","6","7","8","9","0"];
const opInput = ["+", "-", "/","x"];
let layar = '';
let aktif = false;
let indeks = -1;
let memori = "";
let clean = true;
let hapus = 0;

selectContainer[0].addEventListener('click', function(e) {
	let target = e.target.innerHTML;
	if (angkaInput.includes(target) == true) {
		if (clean == true) {
			layar = "";
			clean = false;
		}
		aktif = true;
		let a = target;
		memori += target;
		layar += a;
		selectLayar.innerHTML = layar;
		hapus = 1;
	}
	if ((opInput.includes(target) == true) && (aktif == true)) {
		if (clean == true) {
			let hasil = selectLayar.innerHTML;
			hasil.toString();
			memori += hasil;
			clean = false;
		}
		if (memori.length != 0) {
			let convert = parseInt(memori);
			angkaMem.push(convert);
			memori = "";
		}
		opMem.push(target);
		layar += target;
		selectLayar.innerHTML = layar;
		aktif = false;
		hapus = 2;
	}

	if (e.target.innerHTML == "hapus") {
		if (hapus == 1) {
			memori = memori.slice(0, (memori.length - 1));
		} else if (hapus == 2) {
			opMem.splice(opMem.length-1, 1);
			aktif = true;
		}
		layar = layar.slice(0, (layar.length-1));
		selectLayar.innerHTML = layar;
	}

	if ((target == "Hitung!") && (aktif == true)) {
		if (angkaMem.length != 0) {
			clean = true;
			let convert = parseInt(memori);
			angkaMem.push(convert);
			memori = "";
			let pointer = 0;
			for (let n = 0; n < opMem.length; n++) {
				if (opMem[n] == "x" || opMem[n] == "/") {
					if (opMem[n] == "x") {
						let kali = angkaMem[(n-pointer)] * angkaMem[((n-pointer)+1)];
						angkaMem.splice((n-pointer), 2, kali);
						pointer++;
					}else {
						let bagi = angkaMem[(n-pointer)] / angkaMem[((n-pointer)+1)];
						angkaMem.splice((n-pointer), 2, bagi);
						pointer++;
					}
					if (n == (opMem.length - 1)) {
						for (let hapus = opMem.length; hapus > 0; hapus--) {
							if (opMem.includes("x")) {
								const index = opMem.indexOf("x");
								opMem.splice(index, 1);
							}
							if (opMem.includes("/")) {
								const index = opMem.indexOf("/");
								opMem.splice(index, 1);
							}
						}
					}	
				}
			}
			pointer = 0;
			for (let n2 = 0; n2 < opMem.length; n2++) {
				if ((opMem[n2] == "+") || (opMem[n2] == "-")) {
					if (opMem[n2] == "+") {
						let tambah = angkaMem[(n2-pointer)] + angkaMem[((n2-pointer)+1)];
						angkaMem.splice((n2-pointer), 2, tambah);
						pointer++;
					} else {
						let kurang = angkaMem[(n2-pointer)] - angkaMem[((n2-pointer)+1)];
						angkaMem.splice((n2-pointer), 2, kurang);
						pointer++;
					}
					if (n2 == (opMem.length - 1)) {
						for (let hapus = opMem.length; hapus > 0; hapus--) {
							if (opMem.includes("+")) {
								const index = opMem.indexOf("+");
								opMem.splice(index, 1);
							}
							if (opMem.includes("-")) {
								const index = opMem.indexOf("-");
								opMem.splice(index, 1);
							}
						}
					}
				}
			}
			layar = "";
			layar = angkaMem[0];
			selectLayar.innerHTML = layar;
			angkaMem.splice(0, 1);
		}
	}
});
