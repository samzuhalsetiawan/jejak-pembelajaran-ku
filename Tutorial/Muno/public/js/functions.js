const cards = document.getElementsByClassName("card");
const container = document.getElementsByClassName("container")[0];
const mainJudul = document.querySelector(".head h1");
const h2 = document.getElementsByTagName("h2");
const contents = document.getElementsByClassName("content");
const _menu = document.getElementsByClassName("menu")[0];

// database

let judul = null;
let klik = false;
let changeMenu = true;
let mKlik = false;
let menu = null;
let klikFiller = false;

const hapusOverlay = element => {
    element.addEventListener("click", function(){
        const overlay = document.getElementsByClassName("overlay")[0];
        overlay.remove();
    });
}

function testing() {
    const form = document.getElementsByTagName("form")[0];
    const formData = new FormData(form);
    for (let data of formData.entries()) {
        console.log(data);
    }
}

async function nextPrev(id, metode) {
    let array = null;
    switch (id) {
        case "max-player":
            array = await (await fetch("data/bank-data/maxPlayer")).json();
            break;
        case "hand-card":
            array = await (await fetch("data/bank-data/handCard")).json();
            break;
        case "deck-card":
            array = await (await fetch("data/bank-data/deckCard")).json();
            break;
        case "timeout":
            array = await (await fetch("data/bank-data/timeout")).json();
            break;
    }
    const value = document.getElementById(id);
    const len = array.length;
    const idx = array.indexOf(value.value);
    let angka = null;
    if (metode == "prev") {
        idx == 0 ? angka = array[len - 1] : angka = array[idx - 1];
    } else if (metode == "next") {
        idx == len - 1 ? angka = array[0] : angka = array[idx + 1];
    }
    value.value = angka;
}

async function getKembali() {
    const filler = container.getElementsByClassName("filler")[0];
    if (filler) {
        filler.remove();
    }
    for (let i = 0; i < cards.length; i++) {
        cards[i].classList.remove("showM", "renggang");
    }
    for (let tag of h2) tag.style.display = "none";
    judul = await (await fetch("data/bank-judul/main")).json();
    changeMenu = true;
    mKlik = false;
    menu = null;
    klikFiller = false;
    contents[0].innerHTML = await (await fetch("/text/c1")).text();
    contents[1].innerHTML = await (await fetch("/text/c2")).text();
    contents[2].innerHTML = await (await fetch("/text/c3")).text();
    cards[1].classList.remove("hilang");
    cards[2].classList.remove("hilang");
    cards[0].classList.remove("lebarkan");
}
