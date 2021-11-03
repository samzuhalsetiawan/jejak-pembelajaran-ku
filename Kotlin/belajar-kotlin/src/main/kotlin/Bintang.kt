fun cetakBintang (spasi: Int, bintang: Int) {
    for (space in 1..spasi) {
        print(" ")
    }
    for (star in 1..bintang) {
        print("*")
    }
    println()
}

fun main() {
    var input = 5

    if (input % 2 == 0) input++
    val tinggi = input
    val mid = (tinggi+1)/2
    var spasi = mid - 1

    for (bintang in 1..tinggi step 2) {
        cetakBintang(bintang = bintang, spasi = spasi)
        spasi--
    }
    for (bintang in tinggi downTo 1 step 2) {
        spasi++
        if (bintang == tinggi) continue
        cetakBintang(bintang = bintang, spasi = spasi)
    }
}