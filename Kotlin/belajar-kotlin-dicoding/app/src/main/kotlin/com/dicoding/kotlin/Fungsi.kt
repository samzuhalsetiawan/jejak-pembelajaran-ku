package com.dicoding.kotlin

import javax.naming.Context

fun main() {
    sayHello()
    val tes = Test("1", "2")
    tes.okelah()
}

fun sayHello() = lambdaSayHello {
    println("Hello Dunia")
}

fun lambdaSayHello(lambda: () -> Unit) {
    lambda()
}

data class Test (
    val nama: String,
    val tes: String
        ) {
    fun okelah() {
        println("Hello")
    }
    public val oke = "My"
    internal val me = "lee"
    open val boo = "hehe"
    protected  val yee = "lah"

}

