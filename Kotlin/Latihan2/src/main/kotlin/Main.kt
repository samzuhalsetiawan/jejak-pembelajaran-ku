import java.util.*

fun main() {
    val myLambda: (String) -> String = {
        it.uppercase(Locale.getDefault())
    }
//    println(myLambda("zuhal"))

    fun sayHello(name: String, age: Int) = "Hello $name you are $age"

    val myReferenceLambda: (String, Int) -> String = ::sayHello

//    println(myReferenceLambda("sam", 21))
//    println(sayHello(age = 19, name = "Zuhal"))

    val myLambda2: (Int) -> Int = {
        val result = it * 2
        result
    }

    val myLambda3: (String) -> String = { "Hello $it" }

    println(when (myLambda2(3)) {
        6 -> "enam"
        else -> myLambda2(3)
    })
    println(myLambda3("Setiawan"))

    fun test(nilai: Int): (Int) -> Int {
        val result = nilai * nilai
        return { result + it }
    }

    println(test(3)(2))

    val dummyNum: Int = 10

    fun myPromise(callback: (String) -> Unit, errback: (String) -> Unit) {
        when (dummyNum) {
            5 -> callback("Anda Benar")
            else -> errback("Anda Salah")
        }
    }

    myPromise({ println("Pesannya Adalah $it") }, { println("Errornya adalah $it") })

    infix fun String.lebihTinggiDari(nama: String): String {
        return when (this) {
            "sam" -> "Yap $this lebih tinggi dari $nama"
            else -> "Mana mungkin $this lebih tinggi dari $nama"
        }
    }

    println("sam" lebihTinggiDari "joko")

}