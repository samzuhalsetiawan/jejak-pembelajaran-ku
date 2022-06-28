import Alamat from "./models/Alamat.mjs";
import Customer from "./models/Customer.mjs";
import Genteng from "./models/Genteng.js";
import Rumah from "./models/Rumah.js";
function createLog(msg) {
    const div = document.createElement("div");
    div.textContent = msg;
    return div;
}
const customer = new Customer("Sam Zuhal", new Alamat("Aki Balak"));
const logNama = createLog(customer.getNama());
const logNamaAlamat = createLog(customer.alamat.jalan);
document.body.appendChild(logNama);
document.body.appendChild(logNamaAlamat);
function consoleSayName(customer) {
    const data = customer.sayHello();
    console.log(data);
}
consoleSayName(customer);
console.log(customer.getSMA("SMA1"));
console.log(customer.sd);
const hobby = customer.hobby;
const isSukaGame = customer.isSukaGame();
console.log(hobby.charAt(3).length);
const tes = (tesw) => tesw ? true : false;
console.log(tes(isSukaGame));
const logNamaBos = createLog(customer.sayNamaBos("Sam"));
document.body.appendChild(logNamaBos);
const rumah = new Rumah(3, 20, 20, new Genteng("Bata"));
document.body.appendChild(createLog(rumah.getVolume().toString()));
document.body.appendChild(createLog(rumah.genteng.namaGenteng));
