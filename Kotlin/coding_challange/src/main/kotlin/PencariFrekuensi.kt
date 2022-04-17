fun main() {
    val tepiBawah: Double = 79.5
    val tepiAtas: Double = 94.5

    val data = listOf(20, 26, 28, 36, 42, 46, 54, 56, 64, 64, 72, 76, 84, 84, 86, 90, 90, 94, 94, 94)

    val innerData = data.filter { it.toDouble() > tepiBawah && it.toDouble() < tepiAtas }
    val frekuensi = innerData.size

    println(innerData)
    println(frekuensi)

}