package app

import data.Laptop
import data.Mobil

fun main() {
    val bugati = Mobil("ferari", 2020, "red")
    val hayabusa = Mobil("Honda", 2016, "black")

    bugati.getFullInfo()
    hayabusa.brand = "Suzuki"
    hayabusa.getFullInfo()

    val tuf = Laptop("Asus", "Intel Core i5", "GTX 1550 Ti")
    val rog = Laptop("Asus", "Rayzen R5", "RTX 2660 Ti")

    tuf.getFullInfo()
    rog.getFullInfo()

    val acer = Laptop("IPS")

    acer.getFullInfo()
}