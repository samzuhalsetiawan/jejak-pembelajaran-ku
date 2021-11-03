// const mahasiswa = {
//     nama : "Sam Zuhal Setiawan",
//     NIM : 2005176043,
//     email : "samzuhal095@gmail.com"
// }

// console.log(JSON.stringify(mahasiswa));


let xhr = new XMLHttpRequest();

xhr.onreadystatechange = function() {
    if (xhr.readyState == 4 && xhr.status == 200) {
        let pembeli = JSON.parse(this.responseText);
        console.log(pembeli);
    }
}

xhr.open("GET", "coba.json", true);
xhr.send();
