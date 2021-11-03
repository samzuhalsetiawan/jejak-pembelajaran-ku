const selectInput = document.getElementsByTagName("input");
const selectSubmit = document.getElementsByTagName("button");
const selectDivP = document.getElementsByClassName("keterangan");
let masukanData = false;
let username = [];

function cekInput(selector) {
    if (username.includes(selector.innerHTML) == true) {
        alert("username sudah ada");
        masukanData = false;
    } else {
        selectDivP.style.display = "unset";
        masukanData = true;
    }
}

selectSubmit[0].addEventListener("click", cekInput(selectInput[0]));
selectSubmit[1].addEventListener("click", cekInput(selectInput[1]));
selectSubmit[2].addEventListener("click", cekInput(selectInput[2]));
