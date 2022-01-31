open class Testing1(val name: String) {
    val tes = "Test"
    companion object {
        var middleName = mutableListOf("Sam")
    }
}
class Testing2(val lastName: String) : Testing1("Sam") {
   val tes1 = middleName
   val tes2 = tes
}

fun main() {
    val testing2 = Testing2("Setiawan")
    println(Testing1.middleName)
    testing2.tes1.add("Zuhal")
    println(Testing1.middleName)

}