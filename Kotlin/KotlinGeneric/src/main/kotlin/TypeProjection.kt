class Container<T> (var data: T)

fun <T> copy(from: Container<T>, to: Container<T>) {
    to.data = from.data
}

fun anotherCopy(from: Container<out Any>, to: Container<in Any>) {
    to.data = from.data
}

fun main() {
    val data1: Container<String> = Container("Data1")
    val data2: Container<String> = Container("Data2")
    copy(data1, data2)
    println(data2.data)

    val data3: Container<Boolean> = Container(true)
    val data4: Container<Any> = Container(200)
    anotherCopy(data3, data4)

    println(data3.data)
    println(data4.data)
}