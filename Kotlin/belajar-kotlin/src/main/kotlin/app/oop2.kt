package app

import data.Animal
import data.Person

fun main() {
    val sam = Person("Sam")
    val andi = Person("Sam")
    val joko = Person("Joko")
    val macan = Animal("macan")
    val singa = Animal("macan")
    println(sam == joko)
}