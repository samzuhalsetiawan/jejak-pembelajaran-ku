abstract class Rumah(private val penghuni: Int) {
    abstract val jumlahKamar: Int

    fun hasRoom(): Boolean {
        return penghuni < jumlahKamar
    }

}