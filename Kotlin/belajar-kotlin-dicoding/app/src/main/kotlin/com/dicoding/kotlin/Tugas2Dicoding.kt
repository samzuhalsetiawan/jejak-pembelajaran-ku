package com.dicoding.kotlin

fun main() {
    fun calculate(valueA: Int, valueB: Int, valueC: Int?): Int = valueC?.let { valueA + (valueB - it) } ?: 50

    println(calculate(2,4,8))
}