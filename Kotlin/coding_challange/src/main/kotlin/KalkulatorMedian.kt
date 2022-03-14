fun main() {

    val data = listOf(
        16, 12, 16, 64, 20,
        42, 56, 60, 22, 52,
        50, 18, 78, 82,
        80, 94, 46, 32,
        62, 26, 44, 84
    )

    val dataSorted = data.sorted()
    val panjangData = dataSorted.size

    if (panjangData % 2 == 0) {
        val posisiMedian = panjangData / 2
        val median = (dataSorted[posisiMedian - 1] + dataSorted[posisiMedian]) / 2.0
        println(dataSorted)
        println(median)
    } else {
        val posisiMedian = (panjangData + 1) / 2
        val median = dataSorted[posisiMedian - 1]
        println(dataSorted)
        println(median)
    }

}