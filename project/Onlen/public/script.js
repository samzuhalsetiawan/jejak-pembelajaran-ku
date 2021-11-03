AOS.init({
    duration: 1000
});

const modalBg = document.getElementsByClassName("modal-bg")[0];
const buatKelas = document.getElementById("buat-kelas");
const masukKelas = document.getElementById("masuk-kelas");
const typeText = document.getElementById("type-text");
const teks = "Belajar Kapanpun Dimanapun";
let counter = 0;

const toRoom = async (data) => {
    let redirect = await fetch('/room');
    redirect = await redirect.text();
    if (redirect == "not login") {
        alert("Mohon login terlebih dahulu");
    } else if (redirect == "login") {
        window.location.href = `/room/${data}`
    }
}

const observer1 = new MutationObserver(function(mutationList) {
    for (const mutation of mutationList) {
        if (mutation.type == "childList") {
            const textCount = typeText.textContent.length;
            if (typeText.textContent == teks) {
                const blink = setInterval(() => {
                    counter++;
                    if (counter == 2 || 4) {
                        typeText.classList.toggle("blink");
                    }
                    if (counter == 5) {
                        typeText.textContent = "B";
                        typeText.classList.remove("blink");
                        clearInterval(blink);
                        counter = 0;
                    }
                }, 400);
            } else {
                setTimeout(() => {
                    typeText.textContent += teks[textCount]
                }, 150);
            }
        }
    }
});
observer1.observe(typeText, { childList: true });
typeText.textContent = "B";

buatKelas.addEventListener("click", function() {
    modalBg.classList.add("active");
    const modalClose = document.getElementsByClassName("modal-close-btn")[0];
    modalClose.addEventListener("click", function() {
        modalBg.classList.remove("active");
    });
});
