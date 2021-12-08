class MyData<T>(var name: T) {
    fun showName() {
        println(name)
    }
}

class MyData2 (var name: Any) {
    fun showName() {
        println(name)
    }
}

fun main() {
    var data1: MyData<String> = MyData("Sam")
    var data3: MyData<Int> = MyData(100)
    var data2 = MyData2("Agus")
    val name = data2.name as String  //Jika tidak menggunakan generic, harus di convert dulu

    println(data1.name.length)
    println(name.length)

    data1.name = "Udin"

    data1 = MyData(10)

}

























