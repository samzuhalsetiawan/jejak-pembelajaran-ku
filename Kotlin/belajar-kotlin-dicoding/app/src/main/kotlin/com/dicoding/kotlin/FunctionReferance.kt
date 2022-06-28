package com.dicoding.kotlin

fun main() {
    val numbers = 1.rangeTo(10)
    val evenNumbers = numbers.filter(::isEvenNumber)

    println(evenNumbers)

    val tes = Tes::class.java

    val tes1 = tes
}

class Tes(val name: String)


fun isEvenNumber(number: Int) = number % 2 == 0

/*
   output = [2, 4, 6, 8, 10]
*/