fun main() {
    val listNama = arrayListOf<Person>()

    for (i in 1 until nameList().size) {
        val person = Person(nameList()[i], 20 + i)
        listNama.add(person)
    }

    println(listNama)
}

data class Person (val nama: String, val usia: Int)

fun nameList(): ArrayList<String> {
    val name = arrayListOf<String>()
    name.add("Sam")
    name.add("Zuhal")
    name.add("Setiawan")
    return name
}