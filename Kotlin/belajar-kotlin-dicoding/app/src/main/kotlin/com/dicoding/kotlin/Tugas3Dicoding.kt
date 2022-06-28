package com.dicoding.kotlin

fun <T> checkType(args: T): String {
    return when (args) {
        is Int -> "Yes! it's Integer"
        else -> "Yes! it's ${args?.let { it::class.simpleName }}"
    }
}

fun main() {
    val array: List<Int> = listOf(1,2,3)
    println(checkType("Hello"))
    println(checkType(20))
    println(checkType(20.3))
    println(checkType(array))
    println(checkType(mapOf("saya" to 1)))
    println(checkType(null))
}