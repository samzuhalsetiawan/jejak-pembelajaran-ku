import data.InterfaceKu
import data.objek.Objek1
import data.objek.Objek2

fun main() {

    val tes1 = Objek1.innerObject.properti1
    val tes2 = Objek1("Sam")

    println(tes1)
    println(tes2.name)

    fun runSayApapun(teks: String, data: InterfaceKu) {
        println(data.sayApapun(teks))
    }

    runSayApapun("Sam", object : InterfaceKu{
        override val data1: String = "NamaKu"

        override fun sayApapun(teks: String): String {
            return "${this.data1} adalah $teks"
        }
    })
    runSayApapun("Budi", objekSaya)

    Objek2.showAllData()
}

object objekSaya: InterfaceKu {
    override val data1: String = "Nama Saya"

    override fun sayApapun(teks: String): String {
        return "${this.data1} adalah $teks"
    }
}