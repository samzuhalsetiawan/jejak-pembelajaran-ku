package data

class Person(val nama: String) {
    fun sayNama() {
        println(nama)
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Person -> this.nama == other.nama
            else -> super.equals(other)
        }
    }
}