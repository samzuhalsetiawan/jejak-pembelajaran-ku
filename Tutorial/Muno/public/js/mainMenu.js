if (window.matchMedia("(max-width: 990px)").matches) {
    container.addEventListener("click", function() {
        for (let i = 0; i < cards.length; i++) {
            cards[i].classList.add("renggang");
        }
        for (let tag of h2) tag.style.display = "block";
        mKlik = true;
    });
    for (let i = 0; i < cards.length; i++) {
        cards[i].addEventListener("click", async function() {
            klik = true;
            const renggang = document.getElementsByClassName("renggang");
            if (renggang) {
                for (let j = 0; j < renggang.length; j++) {
                    renggang[j].classList.add("showM");
                }
                if (i == 0) {
                    if (changeMenu && mKlik) {
                        changeMenu = false;
                        menu = "start";
                        judul = await (await fetch("/data/bank-judul/start")).json();
                        contents[0].innerHTML = await (await fetch("/svg/sp")).text() + `<h4>${judul[0]}</h4>`;
                        contents[1].innerHTML = await (await fetch("/svg/mp")).text()  + `<h4>${judul[1]}</h4>`;
                        contents[2].innerHTML = await (await fetch("/svg/lock")).text() + `<h4>${judul[2]}</h4>`;
                    }
                } else if (i == 1) {
                    if (changeMenu && mKlik) {
                        changeMenu = false;
                        cards[1].classList.add("hilang");
                        cards[2].classList.add("hilang");
                        contents[0].innerHTML = await (await fetch("/text/testing")).text();
                    }
                    if (menu == "start") {
                        cards[0].classList.add("lebarkan");
                        cards[1].classList.add("hilang");
                        cards[2].classList.add("hilang");
                        if (!klikFiller) {
                            contents[0].innerHTML = await (await fetch("/form-card")).text();
                            const div = document.createElement("div");
                            div.classList.add("filler");
                            div.setAttribute("style", `height: ${cards[0].clientHeight + 30}px`);
                            container.appendChild(div);
                            klikFiller = true;
                        }
                    }
                } else if (i == 2) {
                    if (changeMenu && mKlik) {
                        changeMenu = false;
                        cards[1].classList.add("hilang");
                        cards[2].classList.add("hilang");
                        contents[0].innerHTML = await (await fetch("/text/testing")).text();
                    }
                }
            }
        });
    }
    document.addEventListener("click", function(e) {
        if (!container.contains(e.target)||e.target == container) {
            getKembali();
        }
    });
} else {
    for (let i = 0; i < cards.length; i++) {
        cards[i].addEventListener("click", async function() {
            klik = true;
            for (let j = 0; j < cards.length; j++) {
                cards[j].classList.add("show");
                for (let content of contents) content.style.display = "initial";
            }
            if (i == 0) {
                if (changeMenu) {
                    changeMenu = false;
                    judul = await (await fetch("data/bank-judul/start")).json();
                    menu = "start";
                    contents[0].innerHTML = await (await fetch("/svg/sp")).text();
                    contents[1].innerHTML = await (await fetch("/svg/mp")).text();
                    contents[2].innerHTML = await (await fetch("/svg/lock")).text();
                }
            } else if (i == 1) {
                if (changeMenu) {
                    changeMenu = false;
                    judul = await (await fetch("data/bank-judul/settings")).json();
                    menu = "settings";
                    cards[0].classList.add("hilang");
                    cards[1].classList.add("lebarkan");
                    cards[2].classList.add("hilang");
                    contents[1].innerHTML = await (await fetch("/text/testing")).text();
                }
                if (menu == "start") {
                    cards[0].classList.add("hilang");
                    cards[1].classList.add("lebarkan");
                    cards[2].classList.add("hilang");
                    if (!klikFiller) {
                        contents[1].innerHTML = await (await fetch("/form-card")).text();
                        const kembali = document.getElementById("kembali");
                        kembali.remove();
                        const div = document.createElement("div");
                        div.classList.add("filler");
                        div.setAttribute("style", `height: ${cards[1].clientHeight + 30}px`);
                        container.appendChild(div);
                        klikFiller = true;
                    }
                }
            } else if (i == 2) {
                if (changeMenu) {
                    changeMenu = false;
                    judul = await (await fetch("data/bank-judul/about")).json();
                    cards[0].classList.add("hilang");
                    cards[1].classList.add("lebarkan");
                    cards[2].classList.add("hilang");
                    contents[1].innerHTML = await (await fetch("/text/testing")).text();
                }
            }
        });
    
        cards[i].addEventListener("mouseover", async function(e) {
            if (!klik) judul = await (await fetch("data/bank-judul/main")).json();
            if (e.target == cards[0]) {
                mainJudul.classList.toggle("shadow");
                mainJudul.innerHTML = judul[0];
                if (!klik) {
                    contents[0].innerHTML = await (await fetch("/svg/sp")).text();
                }
            } else if(e.target == cards[1]) {
                mainJudul.classList.toggle("shadow");
                mainJudul.innerHTML = judul[1];
                if (!klik) {
                    contents[1].innerHTML = await (await fetch("/svg/set")).text();
                }
            } else if(e.target == cards[2]) {
                mainJudul.classList.toggle("shadow");
                mainJudul.innerHTML = judul[2];
                if (!klik) {
                    contents[2].innerHTML = await (await fetch("/svg/about")).text();
                }
            }
        });
    }
    
    container.addEventListener("mouseleave", async function() {
        klik = false;
        const filler = container.getElementsByClassName("filler")[0];
        if (filler) {
            filler.remove();
        }
        for (let i = 0; i < cards.length; i++) {
            cards[i].classList.remove("show");
        }
        mainJudul.innerHTML = "MUno";
        mainJudul.classList.toggle("shadow");
        for (let content of contents) content.removeAttribute("style");
        contents[0].innerHTML = await (await fetch("/text/c1")).text();
        contents[1].innerHTML = await (await fetch("/text/c2")).text();
        contents[2].innerHTML = await (await fetch("/text/c3")).text();
        judul = await (await fetch("data/bank-judul/main")).json();
        changeMenu = true;
        menu = null;
        klikFiller = false;
        cards[0].classList.remove("hilang");
        cards[1].classList.remove("lebarkan");
        cards[2].classList.remove("hilang");
    });
}