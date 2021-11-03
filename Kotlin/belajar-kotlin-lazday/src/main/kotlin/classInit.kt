class Produk (val laptop: String) {
    init {
        addToCart(laptop)
    }
    fun addToCart(produk: String) {
        println("$produk added to cart")
    }
}

fun main() {
    val asus = Produk("ASUS")
    val acer = Produk("ACER")
    println(asus)
}