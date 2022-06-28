import Orang from "./Orang.mjs";
export default class Customer extends Orang {
    constructor(nama, alamat) {
        super("Ngoding", true);
        this.sd = "SD1";
        this.nama = nama;
        this.alamat = alamat;
    }
    getSMA(namaSma) {
        return namaSma;
    }
    sayNamaBos(nama) {
        return "Bos Saya " + nama;
    }
    sayHello() {
        return "Hello " + this.nama;
    }
    getNama() {
        return this.nama;
    }
}
