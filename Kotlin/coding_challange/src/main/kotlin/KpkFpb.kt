

object PerguruanTinggi {
    val data = mapOf(
        "Institut Pendidikan Indonesia" to Pair(9, 60),
        "Institut Teknologi Garut" to Pair(4, 40),
        "Institut Teknologi Indonesia" to Pair(8, 100),
        "Institut Teknologi Nasional Bandung" to Pair(14, 100),
        "Institut Teknologi Nasional Malang" to Pair(9, 40),
        "Institut Teknologi Sepuluh Nopember" to Pair(19, 200),
        "STIKI Malang" to Pair(3, 40),
        "STMIK Widya Pratama" to Pair(2, 40),
        "Telkom University" to Pair(14, 40),
        "Universitas 17 Agustus 1945 Surabaya" to Pair(16, 160),
        "Universitas Ahmad Dahlan" to Pair(10, 60),
        "Universitas Airlangga" to Pair(21, 360),
        "Universitas Al-Azhar Indonesia" to Pair(17, 180),
        "Universitas Alma Ata" to Pair(16, 20),
        "Universitas Amikom Yogyakarta" to Pair(13, 150),
        "Universitas Atma Jaya Yogyakarta" to Pair(12, 40),
        "Universitas BSI" to (10 to 20),
        "Universitas Bhayangkara Jakarta Raya" to (9 to 80),
        "Universitas Bhinneka PGRI" to (10 to 40),
        "Universitas Bina Insani" to (2 to 20),
        "Universitas Brawijaya" to (50 to 100),
        "Universitas Budi Luhur" to (11 to 250),
        "Universitas Diponegoro" to (37 to 200),
        "Universitas Dr. Soetomo Surabaya" to (15 to 60),
        "Universitas Esa Unggul" to (21 to 40),
        "Universitas Ibn Khaldun Bogor" to (16 to 200),
        "Universitas Islam Balitar" to (14 to 20),
        "Universitas Islam Nahdlatul Ulama Jepara" to (14 to 100),
        "Universitas Komputer Indonesia" to (17 to 100),
        "Universitas Langlang buana" to (6 to 80),
        "Universitas Madura" to (11 to 20),
        "Universitas Majalengka" to (6 to 60),
        "Universitas Muhammadiyah Gresik" to (21 to 40),
        "Universitas Muhammadiyah Jakarta" to (21 to 500),
        "Universitas Muhammadiyah Magelang" to (13 to 60),
        "Universitas Muhammadiyah Surabaya" to (12 to 160),
        "Universitas Muria Kudus" to (9 to 100),
        "Universitas Negeri Semarang" to (25 to 200),
        "Universitas Nusa Mandiri" to (2 to 20),
        "Universitas PGRI Semarang" to (14 to 160),
        "Universitas Pamulang" to (13 to 140),
        "Universitas Pancasakti Tegal" to (8 to 20),
        "Universitas Pancasila" to (13 to 60),
        "Universitas Pasundan" to (23 to 100),
        "Universitas Pembangunan Nasional Veteran Jakarta" to (13 to 100),
        "Universitas Pembangunan Nasional Veteran Yogyakarta" to (14 to 80),
        "Universitas Singaperbangsa Karawang" to (19 to 150),
        "Universitas Trisakti" to (19 to 40)
    )
    val kuota = data.values.toList().map { it.second }
    fun getUnivByIndex(index: Int): String {
        return data.keys.toList()[index]
    }
}

data class Info(
    val universitas: String,
    private val originalRatio: Pair<Int, Int>,
    private val ratio: Pair<Int, Int>
) {
    val persentaseLolos: Double = (ratio.second - ratio.first) * 100.0 / ratio.second
    val cetak = "$universitas (${originalRatio.first}/${originalRatio.second} | ${ratio.first}/${ratio.second}) ${persentaseLolos}%"
}

fun main() {
    val kpk = findKpkOf(PerguruanTinggi.kuota)
    val ratio = PerguruanTinggi.data.values.toList()
    val result = ratio.mapIndexed { index, it ->
        val pengali = kpk / it.second
        Info(PerguruanTinggi.getUnivByIndex(index),it, Pair(it.first * pengali, kpk))
    }
    result.sortedByDescending { it.persentaseLolos }.onEachIndexed { index, info ->
        println("${index + 1}. ${info.cetak}")
    }
}

fun primeNumbersUntil(number: Int): List<Int> {
    return when {
        number <= 1 -> listOf()
        else -> (2..number).filterNot { i -> (2 until i).toList().any { i % it == 0 } }
    }
}

fun findKpkOf(numbers: List<Int>): Int {
    val primeNumbers = primeNumbersUntil(500)
    val mappedNumbers = numbers.map {
        var i = it
        var currentPrimeIndex = 0
        val result: MutableList<Int> = mutableListOf()
        while (i > 1) {
            if (i % primeNumbers[currentPrimeIndex] == 0) {
                result.add(primeNumbers[currentPrimeIndex])
                i /= primeNumbers[currentPrimeIndex]
            } else {
                currentPrimeIndex++
            }
        }
        result
    }
    val reducedNumbers = mappedNumbers.reduce { acc, ints ->
        acc.union(ints).toMutableList()
    }
    val factors = reducedNumbers.map {
        it * mappedNumbers.fold(1) { i: Int, ints: MutableList<Int> ->
            ints.filter { prime -> prime == it }.size.let { size ->
                if (size > i) size else i
            }
        }
    }
    return factors.reduce { acc, i -> acc * i }
}