export default class Rumah {
    constructor(tinggi, panjang, lebar, genteng) {
        this.tinggi = tinggi;
        this.panjang = panjang;
        this.lebar = lebar;
        this.genteng = genteng;
    }
    getVolume() {
        return this.tinggi * this.panjang * this.lebar;
    }
}
