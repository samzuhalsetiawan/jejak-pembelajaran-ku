const selectCari = document.getElementsByTagName("button")[0];
const selectInput = document.getElementById("cari");
const selectHasil = document.getElementsByClassName("hasil")[0];

const tampilData = data => {
    selectHasil.innerHTML = data;
}

selectCari.addEventListener("click", function() {
    const xhr = new XMLHttpRequest();

    xhr.onreadystatechange = () => {
        if (xhr.readyState == 4 && xhr.status == 200) {
            tampilData(xhr.response);
        }
    }

    xhr.open("GET", `http://localhost:3000/about/${selectInput.value}`);
    xhr.send();

})