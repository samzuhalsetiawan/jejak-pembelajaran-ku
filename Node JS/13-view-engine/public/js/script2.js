const selectHapus = document.querySelectorAll("td button");
const selectTable = document.querySelector("table");

[...selectHapus].map(e => e.addEventListener("click", function(even){
    const nama = even.target.parentElement.parentElement.children[1].innerHTML;
    // window.location.href = `/contact?del=${nama}`;

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = () => {
        if (xhr.readyState == 4 && xhr.status == 200) {
            const arr = JSON.parse(xhr.response);
            let newElement = `<tr>
            <th>#</th>
            <th>Nama</th>
            <th>NIM</th>
            <th>Option</th>
        </tr>`;
            arr.forEach((e, i)=> {
                newElement += `<tr><td>${++i}</td>
                <td>${e.nama}</td>
                <td>${e.nim}</td>
                <td><button>Hapus</button></td></tr>`
            });
            selectTable.innerHTML = newElement;
        }
    }

    xhr.open("GET", `/contact?del=${nama}`);
    xhr.send();

}));