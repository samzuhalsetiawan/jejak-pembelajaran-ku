import data.Child1a
import data.SuperClass

class Covariant2<T>(val data: T) {
    fun ambilData(): T {
        return this.data
    }
    fun proses(data1: T): T {
        return data1
    }
}

class Contravariant3<T> (val name: T)

fun main() {
    val cov: Covariant2<String> = Covariant2("Data 1")
    val covAny: Covariant2<out Any> = cov
//    val covAny2: Covariant2<Any> = covAny
//    val covInt: Covariant2<Int> = covAny
    println(covAny.data)

    val superclass = SuperClass("superclass")
    val contravariant3: Contravariant3<SuperClass> = Contravariant3(superclass)
    val contravariant4: Contravariant3<in Child1a> = contravariant3

//    Diubah jadi Any?
    val data = contravariant4.name
    println(data)

}
