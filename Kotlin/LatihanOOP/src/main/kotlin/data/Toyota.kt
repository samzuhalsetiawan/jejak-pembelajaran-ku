package data

class Toyota(name: String, tahun: Int): Mobil(name, tahun) {
    fun startEngine(): String = "Engine is Started!"
}