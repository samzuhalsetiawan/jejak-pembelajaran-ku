const selectJmlPersamaan = document.getElementById("jmlPersamaan");
const selectFormInput = document.getElementsByClassName("formInput")[0];
const selectLangkah = document.getElementsByClassName("langkah")[0];
const selectDekomposisiLU = document.getElementsByClassName("dekomposisiLU")[0];
let first = true;

function buatInput() {
    for (let i = 0; i < parseInt(selectJmlPersamaan.value); i++) {
        //div.persamaan i
        let buatTag = document.createElement("div");
        buatTag.classList.add(`persamaan${i + 1}`);
        selectFormInput.appendChild(buatTag);

        //h5
        buatTag = document.createElement("h5");
        let isinya = document.createTextNode(`Persamaan ${i + 1}`);
        buatTag.appendChild(isinya);
        selectFormInput
            .getElementsByClassName(`persamaan${i + 1}`)[0]
            .appendChild(buatTag);

        //div.row
        buatTag = document.createElement("div");
        buatTag.classList.add("row");
        selectFormInput
            .getElementsByClassName(`persamaan${i + 1}`)[0]
            .appendChild(buatTag);

        for (let j = 0; j < parseInt(selectJmlPersamaan.value) + 1; j++) {
            let placeHolder = "kofisien";
            let ariaLabel = `kofisienx${j + 1}`;
            let idnya = `x${j + 1}`;
            let innerHtmlnya = idnya;

            if (j == parseInt(selectJmlPersamaan.value)) {
                placeHolder = "konstanta";
                ariaLabel = `konstanta${i + 1}`;
                idnya = `c${i + 1}`;
                innerHtmlnya = "C";
            }

            //div.col-sm-3
            buatTag = document.createElement("div");
            buatTag.classList.add("col-sm-3");
            selectFormInput
                .querySelector(`.persamaan${i + 1} .row`)
                .appendChild(buatTag);

            //div.input-group
            buatTag = document.createElement("div");
            buatTag.classList.add("input-group");
            selectFormInput
                .querySelectorAll(`.persamaan${i + 1} .row .col-sm-3`)
                [j].appendChild(buatTag);

            //input.form-control
            buatTag = document.createElement("input");
            buatTag.setAttribute("type", "number");
            buatTag.classList.add("form-control");
            buatTag.setAttribute("placeholder", placeHolder);
            buatTag.setAttribute("aria-label", ariaLabel);
            buatTag.setAttribute("aria-describedby", idnya);
            buatTag.setAttribute("required", "true");
            selectFormInput
                .querySelector(
                    `.persamaan${i + 1} .row .col-sm-3:nth-child(${
                        j + 1
                    }) .input-group`
                )
                .appendChild(buatTag);

            //span#xj
            buatTag = document.createElement("span");
            buatTag.classList.add("input-group-text");
            buatTag.setAttribute("id", idnya);
            isinya = document.createTextNode(innerHtmlnya);
            buatTag.appendChild(isinya);
            selectFormInput
                .querySelector(
                    `.persamaan${i + 1} .row .col-sm-3:nth-child(${
                        j + 1
                    }) .input-group`
                )
                .appendChild(buatTag);
        }
    }
    buatTag = document.createElement("div");
    buatTag.classList.add("buttonMulai", "d-grid", "gap-2");
    buatTag.addEventListener("click", eventButtonMulai);
    selectFormInput.appendChild(buatTag);
    buatTag = document.createElement("button");
    buatTag.setAttribute("type", "submit");
    buatTag.classList.add("btn", "btn-success");
    isinya = document.createTextNode("Mulai Hitung!");
    buatTag.appendChild(isinya);
    selectFormInput
        .getElementsByClassName("buttonMulai")[0]
        .appendChild(buatTag);
}

function marginInput() {
    let ulang = parseInt(document.querySelector(".jmlPersamaan input").value);
    for (let i = 0; i < ulang; i++) {
        document.getElementsByClassName(
            `persamaan${i + 1}`
        )[0].style.marginBottom = "15px";
    }
}

function buatMatriks(baris, kolom, namaMatriks) {
    for (let i = 0; i < baris; i++) {
        namaMatriks.push([]);
        for (let j = 0; j < kolom; j++) {
            namaMatriks[i].push(0);
        }
    }
}

function buatTabel(baris, kolom, selector, namaClass, mRumus) {
    let buatTagTable = document.createElement("table");
    buatTagTable.classList.add("matrix");
    if (namaClass != undefined) {
        buatTagTable.classList.add(namaClass);
    }
    if (mRumus != undefined) {
        buatTagTable.classList.add(mRumus);
    }
    for (let i = 0; i < baris; i++) {
        let buatTagTr = document.createElement("tr");
        for (let j = 0; j < kolom; j++) {
            let buatTagTd = document.createElement("td");
            buatTagTr.appendChild(buatTagTd);
        }
        buatTagTable.appendChild(buatTagTr);
    }
    selector.appendChild(buatTagTable);
}

function isiTabel(mode, matriks, selectorTable, xy) {
    if (mode == 1) {
        //ordo nxn > n != 1
        for (let i = 0; i < matriks.length; i++) {
            for (let j = 0; j < matriks.length; j++) {
                selectorTable.querySelector(
                    `tr:nth-child(${i + 1}) td:nth-child(${j + 1})`
                ).innerHTML = matriks[i][j];
            }
        }
    } else if (mode == 2) {
        //ordo nx1
        for (let i = 0; i < matriks.length; i++) {
            selectorTable.querySelector(`tr:nth-child(${i + 1}) td`).innerHTML =
                matriks[i][matriks[i].length - 1];
        }
    } else if (mode == 3) {
        //khusus matriks X
        for (let i = 0; i < matriks.length; i++) {
            selectorTable.querySelector(
                `tr:nth-child(${i + 1}) td`
            ).innerHTML = `${xy + (i + 1)}`;
        }
    }
}

function masukanRumus(baris, barisSU, selector, mode, pembagi1, pembagi2) {
    if (mode == 1) {
        let hasil = pembagi1 / pembagi2;
        if (hasil % 1 != 0) {
            hasil = pembagi1.toString() + "/" + pembagi2.toString();
        }
        let tagSpan = document.createElement("span");
        tagSpan.appendChild(document.createTextNode(hasil));
        selector.innerHTML = `B${baris + 1} - (`;
        selector.appendChild(tagSpan);
        selector.innerHTML += `)B${barisSU + 1}`;
    } else if (mode == 2) {
        selector.innerHTML = `B${barisSU + 1} <=> B${baris + 1}`;
    }
}

function tampilMatriksL() {
    for (let i = 0; i < matriksL.length; i++) {
        for (let j = 0; j < matriksL[i].length; j++) {
            if (j < i) {
                let selectMatriksL = selectLangkah.querySelector(
                    `.dekomposisiLU .dekompL table tr:nth-child(${
                        i + 1
                    }) td:nth-child(${j + 1})`
                );
                let buatSpan = document.createElement("span");
                let isi = selectMatriksL.innerHTML;
                selectMatriksL.innerHTML = "";
                selectMatriksL.appendChild(buatSpan);
                selectMatriksL.getElementsByTagName("span")[0].innerHTML = isi;
            }
        }
    }
}

function clnMatriksL() {
    const selectMatriks = selectLangkah.querySelector(
        ".dekomposisi .dekomposisiLU .dekompL table"
    );
    const cln = selectMatriks.cloneNode(true);
    const selectTarget = selectLangkah.querySelector(
        ".cariMatriksY .cariMY .cariMYML"
    );
    selectTarget.appendChild(cln);
}

function rumusY() {
    for (let i = 0; i < matriksY.length - 1; i++) {
        let buatTagH5 = document.createElement("h5");
        buatTagH5.classList.add("card-title");
        for (let j = 0; j < 2; j++) {
            let buatTagP = document.createElement("p");
            buatTagP.classList.add("card-text");
            switch (j) {
                case 0:
                    for (let k = 0; k < i + 2; k++) {
                        let buatTagSpan = document.createElement("span");
                        let isinya = document.createTextNode(" + ");
                        let isinyaY = document.createTextNode(`(y${k + 1})`);
                        buatTagSpan.classList.add(`l${i + 1}${k}`);
                        if (k != i + 1) {
                            buatTagP.appendChild(buatTagSpan);
                        }
                        buatTagP.appendChild(isinyaY);
                        if (k != i + 1) {
                            buatTagP.appendChild(isinya);
                        }
                    }
                    break;
                case 1:
                    for (let k = 0; k < i + 2; k++) {
                        let buatTagSpan = document.createElement("span");
                        let buatTagSpan2 = document.createElement("span");
                        let isinya = document.createTextNode(" + ");
                        let isinya1 = document.createTextNode("(");
                        let isinya2 = document.createTextNode(")");
                        buatTagSpan.classList.add(`l${i + 1}${k}`);
                        buatTagSpan2.classList.add(`y${i}${k}`);
                        if (k != i + 1) {
                            buatTagP.appendChild(buatTagSpan);
                            buatTagP.appendChild(isinya1);
                            buatTagP.appendChild(buatTagSpan2);
                            buatTagP.appendChild(isinya2);
                            buatTagP.appendChild(isinya);
                        } else {
                            let isinya3 = document.createTextNode(
                                `(y${k + 1})`
                            );
                            buatTagP.appendChild(isinya3);
                        }
                    }
                    break;
            }
            let isiSD = document.createTextNode(" = ");
            let tagHasil = document.createElement("span");
            tagHasil.classList.add(`c${i + 1}`);
            buatTagP.appendChild(isiSD);
            buatTagP.appendChild(tagHasil);
            selectLangkah
                .querySelector(".cariMatriksY .rumusMY")
                .appendChild(buatTagP);
        }
        let isinya4 = document.createTextNode(`y${i + 2} = `);
        let buatTagSpan1 = document.createElement("span");
        buatTagSpan1.classList.add(`y${i + 1}`);
        buatTagH5.appendChild(isinya4);
        buatTagH5.appendChild(buatTagSpan1);
        selectLangkah
            .querySelector(".cariMatriksY .rumusMY")
            .appendChild(buatTagH5);
    }
}

function isirumusKY() {
    let posisi = 0;
    for (let i = 1; i < matriksL.length; i++) {
        for (let j = 0; j < i; j++) {
            for (let k = 0; k < 2; k++) {
                selectLangkah.getElementsByClassName(`l${i}${j}`)[k].innerHTML =
                    selectLangkah.querySelectorAll(
                        ".cariMatriksY .cariMY .cariMYML table span"
                    )[posisi].innerHTML;
            }
            posisi++;
        }
    }
}

function isirumusY() {
    for (let i = 0; i < matriksY.length; i++) {
        if (i < matriksY.length - 1) {
            for (let j = 0; j <= i; j++) {
                selectLangkah.getElementsByClassName(`y${i}${j}`)[0].innerHTML =
                    matriksY[j][0];
            }
        }
        selectLangkah.getElementsByClassName(`y${i}`)[0].innerHTML =
            matriksY[i][0];
        if (i > 0) {
            for (let k = 0; k < 2; k++) {
                selectLangkah.getElementsByClassName(`c${i}`)[k].innerHTML =
                    inputFix[i][inputFix.length];
            }
        }
    }
}

function clnMatriksU() {
    const selectCMatriksU = selectLangkah.querySelector(
        ".cariMatriksX .cariMXMU"
    );
    const selectMatriksU = selectLangkah.querySelector(".dekompU table");
    const cln = selectMatriksU.cloneNode(true);
    selectCMatriksU.appendChild(cln);
}

function clnMatriksX() {
    const selectMatriksX = selectLangkah.querySelector(".matriksXAwal table");
    const selectCMatriksX = selectLangkah.getElementsByClassName("cariMXMX")[0];
    const cln = selectMatriksX.cloneNode(true);
    selectCMatriksX.appendChild(cln);
}

function rumusX() {
    for (let i = 0; i < matriksY.length; i++) {
        let buatTagH5 = document.createElement("h5");
        buatTagH5.classList.add("card-title");
        for (let j = 0; j < 2; j++) {
            let buatTagP = document.createElement("p");
            buatTagP.classList.add("card-text");
            switch (j) {
                case 0:
                    for (let k = 0; k < i + 1; k++) {
                        let buatTagSpan = document.createElement("span");
                        let isinya = document.createTextNode(" + ");
                        let isinyaY = document.createTextNode(
                            `(x${matriksX.length - i + k})`
                        );
                        buatTagSpan.classList.add(`u${i}${k}`);
                        buatTagP.appendChild(buatTagSpan);
                        buatTagP.appendChild(isinyaY);
                        if (k != i) {
                            buatTagP.appendChild(isinya);
                        }
                    }
                    break;
                case 1:
                    for (let k = 0; k < i + 1; k++) {
                        let buatTagSpan = document.createElement("span");
                        let buatTagSpan2 = document.createElement("span");
                        let isinya = document.createTextNode(" + ");
                        let isinya1 = document.createTextNode("(");
                        let isinya2 = document.createTextNode(")");
                        buatTagSpan.classList.add(`u${i}${k}`);
                        buatTagSpan2.classList.add(`x${i}${k}`);
                        buatTagP.appendChild(buatTagSpan);
                        if (k == 0) {
                            let isinya3 = document.createTextNode(
                                `(x${matriksX.length - i})`
                            );
                            buatTagP.appendChild(isinya3);
                        } else {
                            buatTagP.appendChild(isinya1);
                            buatTagP.appendChild(buatTagSpan2);
                            buatTagP.appendChild(isinya2);
                        }
                        if (k != i) {
                            buatTagP.appendChild(isinya);
                        }
                    }
                    break;
            }
            if (i == 0) {
                j = 1;
            }
            let isiSD = document.createTextNode(" = ");
            let tagHasil = document.createElement("span");
            tagHasil.classList.add(`y${i}`);
            buatTagP.appendChild(isiSD);
            buatTagP.appendChild(tagHasil);
            selectLangkah
                .querySelector(".cariMatriksX .rumusMX")
                .appendChild(buatTagP);
        }
        let isinya4 = document.createTextNode(`x${matriksX.length - i} = `);
        let buatTagSpan1 = document.createElement("span");
        buatTagSpan1.classList.add(`x${i}`);
        buatTagH5.appendChild(isinya4);
        buatTagH5.appendChild(buatTagSpan1);
        selectLangkah
            .querySelector(".cariMatriksX .rumusMX")
            .appendChild(buatTagH5);
    }
}

function isiRumusX() {
    for (let i = 0; i < matriksX.length; i++) {
        for (let j = 0; j < i + 1; j++) {
            for (let k = 0; k < 2; k++) {
                selectLangkah.querySelectorAll(`.rumusMX .u${i}${j}`)[
                    k
                ].innerHTML =
                    input[input.length - (1 + i)][input.length - (1 + i) + j];
                if (i == 0) {
                    k = 1;
                }
            }
            if (j != 0) {
                selectLangkah.querySelector(`.rumusMX .x${i}${j}`).innerHTML =
                    matriksX[matriksX.length - i + (j - 1)][0];
            }
        }
        for (let k = 0; k < 2; k++) {
            selectLangkah.querySelectorAll(`.rumusMX .y${i}`)[k].innerHTML =
                matriksY[matriksY.length - (1 + i)][0];
            if (i == 0) {
                k = 1;
            }
        }
        selectLangkah.querySelector(`.rumusMX .x${i}`).innerHTML =
            matriksX[matriksX.length - (1 + i)][0];
    }
}

function buatHasil() {
    for (let i = 0; i < input.length; i++) {
        const selectHasil = document.getElementsByClassName("hasilnya")[0];
        let buatTagInput = document.createElement("input");
        let buatTagSpan = document.createElement("span");
        let buatTagDiv = document.createElement("div");
        let isinya = document.createTextNode(`x${i + 1} = `);
        buatTagInput.setAttribute("type", "text");
        buatTagInput.classList.add("form-control");
        buatTagInput.setAttribute("aria-label", `x${i + 1}`);
        buatTagInput.setAttribute("aria-describedby", `x${i + 1}`);
        buatTagInput.setAttribute("readonly", "true");
        buatTagInput.setAttribute("value", `${matriksX[i][0]}`);
        buatTagSpan.classList.add("input-group-text");
        buatTagSpan.setAttribute("id", `x${i + 1}`);
        buatTagDiv.classList.add("input-group");
        buatTagSpan.appendChild(isinya);
        buatTagDiv.appendChild(buatTagSpan);
        buatTagDiv.appendChild(buatTagInput);
        selectHasil.appendChild(buatTagDiv);
    }
}

function khususX() {
    document.getElementsByClassName(
        "khususX"
    )[0].innerHTML = `x${input.length}`;
}

function jikaNol(barisSUNol, barisTukar) {
    let buatTagDiv = document.createElement("div");
    for (let i = 0; i < 2; i++) {
        let buatTagTable = document.createElement("table");
        for (let j = 0; j < input.length; j++) {
            let buatTagTr = document.createElement("tr");
            for (let k = 0; k < input.length; k++) {
                let buatTagTd = document.createElement("td");
                if (i == 1) {
                    let isiTd = document.createTextNode(" ");
                    if (j == barisSUNol) {
                        isiTd = document.createTextNode(
                            `B${barisSUNol} \u2B0C B${barisTukar}`
                        );
                    }
                    buatTagTd.appendChild(isiTd);
                    buatTagTr.appendChild(buatTagTd);
                    break;
                } else {
                    let isiTd = document.createTextNode(`${input[j][k]}`);
                    buatTagTd.appendChild(isiTd);
                    buatTagTr.appendChild(buatTagTd);
                }
            }
            buatTagTable.appendChild(buatTagTr);
        }
        if (i == 0) {
            buatTagTable.classList.add("matrix");
        } else {
            buatTagTable.classList.add("matrix", "mRumus");
        }
        buatTagDiv.appendChild(buatTagTable);
    }
    buatTagDiv.classList.add("SUNol");
    const select = selectLangkah.getElementsByClassName("allTabelLangkah")[0];
    const select1 = selectLangkah.getElementsByClassName(
        `langkah${barisSUNol}`
    )[0];
    select.insertBefore(buatTagDiv, select1);
}

//lakukan pengecekan tak konsisten dan banyak penyelesaian
function cekPenyelesaian() {
    for (let i = 0; i < input.length; i++) {
        let cek = false;
        for (let j = 0; j < input.length; j++) {
            if (input[i][j] != 0) {
                cek = true;
                break;
            }
        }
        if (cek == false) {
            if (matriksY[i][0] == 0) {
                return "banyak-penyelesaian";
            } else {
                return "tak-konsisten";
            }
        }
    }
}

function isiAlert(kondisi) {
    if (kondisi == "banyak-penyelesaian") {
        document.getElementById("headerAlert2").innerHTML = "";
        document.getElementById("headerAlert2").innerHTML =
            "SPL ini memiliki banyak penyelesaian";
        document.getElementById("sd").classList.toggle("sd", false);
        document.getElementById("tsd").classList.toggle("tsd", true);
    } else if (kondisi == "tak-konsisten") {
        document.getElementById("headerAlert2").innerHTML = "";
        document.getElementById("headerAlert2").innerHTML =
            "SPL ini tidak memiliki penyelesaian/tak konsisten";
        document.getElementById("sd").classList.toggle("sd", true);
        document.getElementById("tsd").classList.toggle("tsd", false);
    }
}

function cekCheckbox() {
    let cek = document.getElementById("switch1");
    if (cek.checked == false) {
        for (let i = 0; i < 5; i++) {
            document
                .getElementsByClassName("hiddenStep")
                [i].classList.add("hiddenTrue");
        }
    } else if (cek.checked == true) {
        for (let i = 0; i < 5; i++) {
            document
                .getElementsByClassName("hiddenStep")
                [i].classList.remove("hiddenTrue");
        }
    }
}

selectLangkah.setAttribute("style", "display: none");
document.getElementById("alert1").setAttribute("style", "display: none");
document.getElementById("alert2").setAttribute("style", "display: none");

function reset() {
    selectLangkah.querySelector(".matriksAwal table").remove();
    selectLangkah.querySelector(".matriksXAwal table").remove();
    selectLangkah.querySelector(".matriksCAwal table").remove();
    selectLangkah.getElementsByClassName("allTabelLangkah")[0].innerHTML = "";
    selectLangkah.querySelector(".dekompA table").remove();
    selectLangkah.querySelector(".dekompU table").remove();
    selectLangkah.querySelector(".dekompL table").remove();
    selectLangkah.querySelector(".cariMYML table").remove();
    selectLangkah.querySelector(".cariMYMY table").remove();
    selectLangkah.querySelector(".cariMYMC table").remove();
    selectLangkah.querySelector(".rumusMY .y0").innerHTML = "";
    let selectP = selectLangkah.querySelectorAll(".rumusMY p");
    for (let i = 0; i < selectP.length; i++) {
        selectP[i].remove();
    }
    let selectH5 = selectLangkah.querySelectorAll(".rumusMY h5");
    for (let i = 0; i < selectH5.length; i++) {
        if (i != 0) {
            selectH5[i].remove();
        }
    }
    selectLangkah.querySelector(".cariMXMU table").remove();
    selectLangkah.querySelector(".cariMXMX table").remove();
    selectLangkah.querySelector(".cariMXMY table").remove();
    selectLangkah.getElementsByClassName("khususX")[0].innerHTML = "";
    selectLangkah.getElementsByClassName("rumusMX")[0].innerHTML = "";
    selectLangkah.querySelector(".hasil .hasilnya").innerHTML = "";
    document.getElementById("alert1").setAttribute("style", "display: none");
    document.getElementById("alert2").setAttribute("style", "display: none");
}
