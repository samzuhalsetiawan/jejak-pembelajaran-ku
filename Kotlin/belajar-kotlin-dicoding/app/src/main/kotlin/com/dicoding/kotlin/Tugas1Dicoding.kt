package com.dicoding.kotlin

fun main() {
    fun isEven(numberTested: Int) = numberTested % 2 == 0

    println(isEven(4))
    println(isEven(3))
    println(isEven(1))
    println(isEven(0))
    println(isEven(11))
    println(isEven(12))
}
