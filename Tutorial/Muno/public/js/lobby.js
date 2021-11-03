const closeButton = document.querySelectorAll('[data-close-button]');
const overlay = document.getElementsByClassName("overlay")[0];

closeButton.forEach(tombol => {
    tombol.addEventListener("click", function() {
        //TODO masukan dahulu nama ke database
        tombol.closest(".modal").classList.remove("aktif");
        overlay.classList.remove("ovAktif");
    });
});