//perbedaan object dgn class adalah pada object tidak ada constructor
object Laptop {
    val name: String = "ACER"
    val cpu: String = "Intel"
    var angka: Int = 1
    init {
        println(getInfoName())
    }

    fun getInfoName(): String {
        return "Merk = $name"
    }

    fun getInfoCpu(): String {
        return "Processor = $cpu"
    }

}

//perbedaan lainnya adalah object tidak perlu di instansiasi bisa langsung ngambil properti

fun main() {

    println("first")

    val brand = Laptop.name

    println(brand)

    println(Laptop.cpu)

    val cpu = Laptop.cpu

    println(cpu)

    println(Laptop.getInfoCpu())

    //pada object semua propertinya statis

    println(Laptop.angka)

    Laptop.angka = 2

    println(Laptop.angka)

    val brand2 = Laptop.angka

    println(brand2)

    //object di kotlin mirip static class di PHP ?
}