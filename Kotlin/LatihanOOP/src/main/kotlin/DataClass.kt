import data.Mahasiswa

fun main() {
    val sam = Mahasiswa("Sam", 2005176043)
    val udin = sam.copy(nama = "Udin")

    fun Mahasiswa.sayHello(name:String): String = "Hello $name, my name is ${this.nama}"

    val res = udin.sayHello("Sam")

    println(res)

}