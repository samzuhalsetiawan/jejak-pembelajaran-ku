const pilihanAbout = document.getElementsByClassName("about")[0];
const pilihanContact = document.getElementsByClassName("contact")[0];

pilihanAbout.addEventListener("click", function() {
    document.location.href = "/about";
});

pilihanContact.addEventListener("click", function() {
    document.location.href = "/contact";
})