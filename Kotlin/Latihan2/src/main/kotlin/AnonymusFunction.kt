fun main() {

//    Lambda tidak bisa menggunakan return
//    line terakhir dari lambda otomatis return
    val a = { println("Hello World") }
    val b = { "Hello World" }

//    Anonymous function bisa menggunakan return
    val c = fun() {
        println("Hello World")
    }
    val d = fun(): String {
        return "Hello World"
    }
    val e = fun(): Boolean = true //one line anonymous function

//    Type dari anonymous function dan lambda
    var f: () -> Int
    f = { 2 }
    f = fun(): Int {
        return 3
    }
    f = fun() = 4

// pembuktian bahwa function, anonymus function dan lambda itu sama
//    Bahkan bila input dan output function, anonymous function sama dengan lambda, hashcode mereka sama
    val g = {
        println("Hello World")
        print("")
    }

    fun sayHelloWorld() {
        println("Hello World")
    }

    println(a().hashCode())
    println(b())
    println(c().hashCode())
    println(d())
    println(e())
    println(f())
    println(g().hashCode())
    println(sayHelloWorld().hashCode())

}