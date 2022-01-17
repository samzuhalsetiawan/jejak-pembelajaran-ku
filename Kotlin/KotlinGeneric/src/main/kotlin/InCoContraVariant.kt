import data.Child1a
import data.Child1b
import data.Child2a
import data.SuperClass

class Covariant<out T>(val name: T) {

//    Tidak Boleh mengganti name setelah class ini di instansiasi

    fun hanyaBolehReturnT(teks: String): T {
//        Tidak boleh menerima T sebagai parameter function
        println(teks)
        return this.name
    }


}

class Contravariant<in T>(val name: String) {

//    Tidak Boleh memberi contructor T

    fun hanyaBolehMenerimaT(teks: String, data: T) {
//        Hanya boleh menerima T sebagai parameter function
        val tes = data
        println(data)
        println(teks)
        when (data) {
            is String -> println("Panjang String ${data.length}")
        }
    }

}

fun main() {
    val covariant: Covariant<String> = Covariant("Sam")
    val covariantAny: Covariant<Any> = covariant
    println(covariantAny.hanyaBolehReturnT("LOL"))



    val contravariant: Contravariant<SuperClass> = Contravariant("Samsul")
    val contravariantAny: Contravariant<Child1a> = contravariant
    contravariantAny.hanyaBolehMenerimaT("Contravariant", Child2a("Tes"))

    val contravariant2: Contravariant<Child1a> = contravariant
    contravariant2.hanyaBolehMenerimaT("Contravariant2", Child1a("Tes2"))

    val contravariant3: Contravariant<String> = Contravariant("Alex")
    contravariant3.hanyaBolehMenerimaT("tes", "nama yang panjang")

}