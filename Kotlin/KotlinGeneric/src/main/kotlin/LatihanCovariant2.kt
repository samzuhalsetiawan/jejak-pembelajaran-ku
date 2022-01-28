
class Testing<out T>(val data: T) {
    fun ambilData(): T {
        return data
    }
}

open class Bapak {
    fun sayHai() = "Hai"
}

class Anak : Bapak() {
    fun sayHello() = "Hello Dunia"
}

fun main() {

    val testing1 = Testing("Sam")
    val data1 = testing1.ambilData()

    val testing2: Testing<Any> = testing1
    val data2 = testing2.ambilData()

    val anak = Anak()

    anak.sayHai()

    val tes1: Bapak = anak

}