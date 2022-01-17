
open class Employee(var name: String) {
    private val nip = 200
    fun sayNip() = println("Hello ${this.nip}")
}
class Manager(name: String): Employee(name)
class Sales(name: String): Employee(name)

fun main() {
    var karyawan: Employee = Employee("Sam")
    var sales: Sales = Sales("Joko")
    var manager = Manager("Agus")
    karyawan.sayNip()
    karyawan = Manager("Udin")
    karyawan.sayNip()
    karyawan = sales
//    sales = manager  //Error
//    sales = karyawan //Error
    karyawan = Employee("Rio")

//    ERROR
//    var karyawan: Manager = Manager("Sam")
//    karyawan.sayNip()
//    karyawan = Employee("Budi")

    println(karyawan.name)
}