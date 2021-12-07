import data.*

fun main() {
    val avanza = Avanza("avanza", 2011)
    val toyota = Toyota("toyota", 2010)
    val mitsubisi = Mitsubisi("mitsubisi", 2009)

    val level1 = Level1("tes-1")
    val saudara1 = Saudara1("saudara-1")

    fun testing(objek: Mobil): String {
        return when(objek) {
            is Avanza -> "ini Mobil Avanza"
            is Toyota -> "Ini Mobil Toyota"
            else -> "ini Mobil Lainnya"
        }
    }

    fun testing2(objek: TopLevel) {
        when (objek) {
            is Level1 -> println("Objek ini Level 1")
            is Saudara1 -> println("Onjek ini Saudara 1")
            else -> println("none of these")
        }
    }

    fun testing3(objek: Any) {
        when (objek) {
            is Mobil -> println("Ini adalah kelas turunan Mobil")
            is TopLevel -> println("Ini adalah kelas turunan TopLevel")
            else -> println("None of these")
        }
    }

    println(repeat(12) { print("=") })
    println(testing(avanza))
    println(testing(toyota))
    println(repeat(12) { print("=") })
    testing2(level1)
    testing2(saudara1)
    println(repeat(12) { print("=") })
    testing3(level1)
    testing3(saudara1)

}