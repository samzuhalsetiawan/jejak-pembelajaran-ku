fun main() {

    val data = listOf(
        16, 94, 22, 42, 26,
        82, 50, 16, 32, 80 ,
        60, 84, 62, 78,
        20, 12, 46, 52,
        56, 44, 18, 64
    )

//    data 1 sorted = [20, 26, 28, 36, 42, 46, 54, 56, 64, 64, 72, 76, 84, 84, 86, 90, 90, 94, 94, 94]
//    data 2 sorted = [12, 16, 16, 18, 20, 22, 26, 32, 42, 44, 46, 50, 52, 56, 60, 62, 64, 78, 80, 82, 84, 94]

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