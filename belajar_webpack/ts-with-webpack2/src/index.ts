import ButtonBilangan from "./models/ButtonBilangan";
import ButtonOperator from "./models/ButtonOperator";
import ButtonTotal from "./models/ButtonTotal";

const satu = new ButtonBilangan("satu", "1")
const dua = new ButtonBilangan("dua", "2")
const tiga = new ButtonBilangan("tiga", "3")
const empat = new ButtonBilangan("empat", "4")
const lima = new ButtonBilangan("lima", "5")
const enam = new ButtonBilangan("enam", "6")
const tujuh = new ButtonBilangan("tujuh", "7")
const delapan = new ButtonBilangan("delapan", "8")
const sembilan = new ButtonBilangan("sembilan", "9")
const nol = new ButtonBilangan("nol", "0")

const tambah = new ButtonOperator("tambah", "+")
const bagi = new ButtonOperator("bagi", "/")
const kali = new ButtonOperator("kali", "*")
const kurang = new ButtonOperator("kurang", "-")

const total = new ButtonTotal("total", "=")