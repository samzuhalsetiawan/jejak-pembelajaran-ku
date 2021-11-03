//acbac9ba

const selectSearch = document.querySelector(".container input");
const selectHasil = document.getElementsByClassName("hasil")[0];

function masukanData (data) {
    let isi = '';
    if (data.Search){
        for (let i = 0; i < data.Search.length; i++) {
            isi += `
            <div>
                <img src="${data.Search[i].Poster}" alt="poster">
                <h1>${data.Search[i].Title}</h1>
                <p>${data.Search[i].Year}</p>
            </div>
            `;
        }
        selectHasil.innerHTML = isi; 
    } else {
        selectHasil.innerHTML = `<h1>Film tidak ditemukan</h1>`; 
    }
}

// Error handling return Promise dengan then dan catch
function ambilData (judul) {
    return fetch(`http://www.omdbapi.com/?apikey=acbac9ba&s=${judul}`)
    .then(response => response.json().then(response => response))
    .catch(response => response);
}

// asyncronous function, error handling dengan try and catch
// async function ambilData (judul) {
//     try {
//         const response = await fetch(`http://www.omdbapi.com/?apikey=acbac9ba&s=${judul}`);
//         const response_1 = await response.json();
//         return response_1;
//     } catch (response_2) {
//         return response_2;
//     }
// }

selectSearch.addEventListener("keyup", async function(e) {
    if (e.keyCode == 13) {
        const judul = selectSearch.value;
        const data = await ambilData(judul);
        masukanData(data);
    }
});
