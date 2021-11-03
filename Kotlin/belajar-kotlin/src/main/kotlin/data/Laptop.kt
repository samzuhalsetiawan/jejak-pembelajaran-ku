package data

class Laptop(val merk: String, val cpu: String, val vga: String) {

    init {
        println("Object $merk dibuat")
    }

    var info: String = "$merk $cpu $vga"

    constructor(screen: String): this("Acer", "Intel", "HD Graphic") {
        val layar: String = screen
        info = "$merk $cpu $vga $layar"
    }

    fun getFullInfo() {
        println(info)
    }
}