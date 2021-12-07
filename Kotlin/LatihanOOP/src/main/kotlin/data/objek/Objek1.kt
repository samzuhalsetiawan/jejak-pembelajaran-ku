package data.objek

class Objek1(val name: String) {
    object innerObject {
        val properti1 = "properti 1"
        fun sayHello() {
            println("Hello World")
        }
    }
}