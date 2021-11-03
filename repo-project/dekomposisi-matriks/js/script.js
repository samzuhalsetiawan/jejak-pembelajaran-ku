/////////////////////////////////////////////////////////////
//SPL Dekomposisi Matriks oleh Sam Zuhal Setiawan 2005176043
/////////////////////////////////////////////////////////////

//membuat array untuk matriks
let input = [];     //matriks untuk melakukan OBE
let inputFix = [];  //matriks A
let matriksU = [];
let matriksL = [];
let matriksY = [];
let matriksX = [];

//menambahkan eventListener
selectFormInput.innerHTML = "";
buatInput();
marginInput();
selectJmlPersamaan.addEventListener("keyup", function () {
    selectFormInput.innerHTML = "";
    buatInput();
    marginInput();
});
selectJmlPersamaan.addEventListener("change", function () {
    selectFormInput.innerHTML = "";
    buatInput();
    marginInput();
});

//Logika perhitungannya dimulai disini
function eventButtonMulai() {

    //cek apakah data yang dimasukan angka
    let cekAngka = selectFormInput.getElementsByTagName("input");
    for (let i = 0; i < cekAngka.length; i++) {
        if (
            cekAngka[i].value == undefined ||
            cekAngka[i].value == NaN ||
            cekAngka[i].value == ""
        ) {
            selectLangkah.setAttribute("style", "display: none");
            return false;
        } else {
            selectLangkah.removeAttribute("style");
        }
    }

    //untuk mereset tampilan
    if (first == false) {
        reset();
    }
    first = false;

    //mereset kembali matriks
    input = [];
    inputFix = [];
    matriksU = [];
    matriksL = [];
    matriksY = [];
    matriksX = [];

    //Isi nilai Awal matriks dengan Nol
    buatMatriks(parseInt(selectJmlPersamaan.value), parseInt(selectJmlPersamaan.value)+1, input);
    buatMatriks(parseInt(selectJmlPersamaan.value), parseInt(selectJmlPersamaan.value)+1, inputFix);
    buatMatriks(parseInt(selectJmlPersamaan.value), 1, matriksY);
    buatMatriks(parseInt(selectJmlPersamaan.value), 1, matriksX);
    buatMatriks(parseInt(selectJmlPersamaan.value), parseInt(selectJmlPersamaan.value), matriksU);
    buatMatriks(parseInt(selectJmlPersamaan.value), parseInt(selectJmlPersamaan.value), matriksL);

    //isi matriks L dengan diagonal utama 1
    for (let i = 0; i < matriksL.length; i++) {
        matriksL[i][i] = 1;
    }

    //memasukan data ke dalam array input(matriks A)
    for (let k = 0; k < 2; k++) {
        let arr = input;
        if (k == 1) {
            arr = inputFix;
        }
        for (let i = 0; i < arr.length; i++) {
            for (let j = 0; j < arr[i].length; j++) {
                arr[i][j] = parseFloat(document.querySelectorAll(".formInput input")[j+i*arr[i].length].value);
            }
        }
    }

    //Tampilkan matriks di layar
    buatTabel(input.length, input.length, selectLangkah.getElementsByClassName("matriksAwal")[0]);
    isiTabel(1, input, selectLangkah.querySelector(".matriksAwal table"));
    buatTabel(input.length, 1, selectLangkah.getElementsByClassName("matriksXAwal")[0]);
    isiTabel(3, input, selectLangkah.querySelector(".matriksXAwal table"), "x");
    buatTabel(input.length, 1, selectLangkah.getElementsByClassName("matriksCAwal")[0]);
    isiTabel(2, input, selectLangkah.querySelector(".matriksCAwal table"));
    buatTabel(inputFix.length, inputFix.length, selectDekomposisiLU.getElementsByClassName("dekompA")[0]);
    buatTabel(matriksU.length, matriksU.length, selectDekomposisiLU.getElementsByClassName("dekompU")[0]);
    buatTabel(matriksL.length, matriksL.length, selectDekomposisiLU.getElementsByClassName("dekompL")[0]);

    // proses dekomposisi (sampai line )
    for (let i = 0; i < input.length; i++) {
        //Cek jika diagonal utama ada Nol, Tukar kalau ada
        if (input[i][i] == 0) {
            for (let cariAngka = i + 1; cariAngka < input.length; i++) {
                if (input[cariAngka][i] != 0) {
                    jikaNol(i, cariAngka);
                    //tukar dimatriks A
                    [input[i], input[cariAngka]] = [input[cariAngka], input[i]];
                    //tukar diMatriks L
                    for (let j = 0; j < i; j++) {
                        [matriksL[i][j], matriksL[cariAngka][j]] = [matriksL[cariAngka][j], matriksL[i][j]];
                    }
                    //tukar dimatriks C
                    [inputFix[i], inputFix[cariAngka]] = [inputFix[cariAngka], inputFix[i]];
                    break;
                }
            }
        }

        //tampilkan matriks dilayar(blm ada isi)
        buatTabel(input.length, input.length, selectLangkah.querySelector(".dekomposisi .card-body .allTabelLangkah"),`langkah${i}`);
        isiTabel(1, input, selectLangkah.querySelector(`.dekomposisi .card-body .allTabelLangkah table.langkah${i}`));
        isiTabel(1, input, selectLangkah.querySelector(`.dekomposisi .dekomposisiLU .dekompU table`));
        buatTabel(input.length, 1, selectLangkah.querySelector(".dekomposisi .card-body .allTabelLangkah"),`rumus${i}`,"mRumus");

        //proses dekomposisi matriks A menjadi matriks U
        for (let j = i + 1; j < input.length; j++) {
            let pembagi = input[j][i] / input[i][i];
            //Tampilkan rumusnya
            masukanRumus(
                j, i, 
                selectLangkah.querySelector(`.dekomposisi .card-body .allTabelLangkah table.rumus${i} tr:nth-child(${j + 1}) td`),
                1, input[j][i], input[i][i]
            );

            //(membuat matriks L)masukan faktor pembagi kedalam mariks L
            matriksL[j][i] = pembagi;

            //logika program untuk Operasi Baris Elementer
            for (let k = 0; k < input.length; k++) {
                input[j][k] = input[j][k] - pembagi * input[i][k];
            }
        }
    }
    //tampilkan matriks dilayar(sudah ada isi/sesudah dilakukan OBE)
    isiTabel(1, inputFix, selectDekomposisiLU.querySelector(`.dekompA table`));
    isiTabel(1, matriksL, selectLangkah.querySelector(`.dekomposisi .dekomposisiLU .dekompL table`));
    tampilMatriksL();
    clnMatriksL();
    buatTabel(matriksY.length, 1, selectLangkah.querySelector(".cariMatriksY .cariMYMY"));
    isiTabel(3, matriksY, selectLangkah.querySelector(".cariMatriksY .cariMYMY table"),"y");
    buatTabel(matriksY.length, 1, selectLangkah.querySelector(".cariMatriksY .cariMYMC"));
    isiTabel(2, inputFix, selectLangkah.querySelector(".cariMatriksY .cariMYMC table"));

    //Mencari nilai matriks Y
    for (let i = 0; i < matriksL.length; i++) {
        if (i == 0) {
            matriksY[i][0] = inputFix[i][inputFix.length];
        } else {
            let hasil = inputFix[i][inputFix.length];
            for (let j = 0; j < i; j++) {
                hasil -= matriksY[j][0] * matriksL[i][j];
            }
            matriksY[i][0] = hasil;
        }
    }

    //cek jika terdapat banyak penyelesaian atau tak konsisten
    if (cekPenyelesaian() == "banyak-penyelesaian") {
        document.getElementById("alert1").removeAttribute("style");
        document.getElementById("alert2").removeAttribute("style");
        isiAlert("banyak-penyelesaian");
        document.getElementById("switch1").checked = false;
    } else if (cekPenyelesaian() == "tak-konsisten") {
        document.getElementById("alert1").removeAttribute("style");
        document.getElementById("alert2").removeAttribute("style");
        isiAlert("tak-konsisten");
        document.getElementById("switch1").checked = false;
    } else {
        document.getElementById("switch1").checked = true;
    }

    //Perlihatkan proses mencari matriks Y
    rumusY();
    isirumusKY();
    isirumusY();
    clnMatriksU();
    clnMatriksX();
    buatTabel(matriksY.length, 1, selectLangkah.getElementsByClassName("cariMXMY")[0]);
    isiTabel(2, matriksY, selectLangkah.querySelector(".cariMXMY table"));

    //Mencari nilai matriks X
    for (let i = 0; i < matriksX.length; i++) {
        let pengurang = 0;
        for (let j = 0; j < input.length; j++) {
            pengurang += input[input.length - (1 + i)][j] * matriksX[j][0];
        }
        matriksX[matriksX.length - (1 + i)][0] =
            (matriksY[matriksY.length - (1 + i)][0] - pengurang) / input[input.length - (1 + i)][input.length - (1 + i)];
    }

    //Perlihatkan proses mencari matriks X
    rumusX();
    isiRumusX();
    buatHasil();
    khususX();
    cekCheckbox();
}

/////////////////////////////////////////////////////////////////////////////////////////////
//file (script.js) ini khusus untuk menampung logika perhitungan
//sedangkan file gui.js khusus untuk tampilan dan selector DOM
//kedua file ini terhubung melalui index.html
////////////////////////////////////////////////////////////////////////////////////////////
