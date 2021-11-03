package data

class Mobil(var brand: String,
            var year: Int,
            var color: String) {
    fun getFullInfo() {
        println(brand)
        println(year)
        println(color)
    }
}