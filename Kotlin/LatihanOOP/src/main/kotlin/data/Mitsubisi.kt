package data

class Mitsubisi(name: String, tahun: Int): Mobil(name, tahun) {
    fun stopEngine(): String = "Engine is Stop"
}