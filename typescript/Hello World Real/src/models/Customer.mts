import Alamat from "./Alamat.mjs";
import Human from "./Human.mjs";
import Karyawan from "./Karyawan.js";
import Orang from "./Orang.mjs";
import Sekolah from "./Sekolah.mjs";

export default class Customer extends Orang implements Human, Karyawan {
  sd: string = "SD1";
  getSMA(namaSma: string): string {
    return namaSma
  }
  private nama: string;
  public alamat: Alamat;
  constructor(nama: string, alamat: Alamat) {
    super("Ngoding", true);
    this.nama = nama;
    this.alamat = alamat;
  }
  sayNamaBos(nama: string): string {
    return "Bos Saya " + nama;
  }
  sayHello(): string {
    return "Hello " + this.nama;
  }
  public getNama(): string {
    return this.nama;
  }
}