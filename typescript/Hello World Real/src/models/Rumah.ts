import Genteng from "./Genteng"

export default class Rumah {
  public tinggi: number
  private lebar: number
  public panjang: number
  public genteng: Genteng
  constructor(tinggi: number, panjang: number, lebar: number, genteng: Genteng) {
    this.tinggi = tinggi
    this.panjang = panjang
    this.lebar = lebar
    this.genteng = genteng
  }
  public getVolume(): number {
    return this.tinggi * this.panjang * this.lebar
  }
}