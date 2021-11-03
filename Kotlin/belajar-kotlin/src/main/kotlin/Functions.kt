//membuat function
fun union (array: Array<Int>, angka: Int): Boolean {
    for (data in array) {
        if (data == angka) {
            return true
        }
    }
    return false
}

//menggunakan vararg
fun jumlakanSemua ( vararg angka: Int): Int {
    var total = 0
    for (ang in angka) {
        total += ang
    }
    return total
}

//menggunakan oneline function
fun cetak (teks: String): Unit = println(teks)

//mengunakan extension function
fun Int.pangkatDua(): Int = this*this

fun main() {
    val arrayku: Array<Int> = arrayOf(1,2,3,4,5)
    val myAngka = 6

    if (union(arrayku, myAngka)) {
        cetak("Benar ini")
    } else {
        cetak("Salah ini")
    }

    println(jumlakanSemua(1,2,3,4,5,10))

    println(10.pangkatDua())
}