import data.Level1
import data.Level2
import data.Saudara1
import data.TopLevel

fun main() {
    val tesTop = TopLevel("top-level")
    val tes1 = Level1("tes-1")
    val tes2 = Level2("tes-2")
    val saudara1 = Saudara1("saudara-1")

    functionTesting(saudara1)

}

fun functionTesting(any: TopLevel) {
//    when (any) {
//        is Level1 -> {
//            println(any.propertiTop)
//            any.function1()
//        }
//        else -> println(any)
//    }
//    val test = any as? Level1
//    println(test?.propertiTop)
//    test?.function1()
    println(any.propertiTop)
    any.functionTop()
}