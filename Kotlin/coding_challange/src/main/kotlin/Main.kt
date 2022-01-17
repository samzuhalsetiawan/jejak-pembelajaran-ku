import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

const val INPUT_COUNT = 1
const val INPUT_NUMBER = 5237


fun main() {
    val time = measureTimeMillis {
//    val inputCount = (readLine()!!).toInt()
        val inputCount = INPUT_COUNT
        val primeNumbers = allPrimeNumbersTo(10000)
        repeat(inputCount) {
//        val inputNumber = (readLine()!!).toInt()
            val inputNumber = INPUT_NUMBER
            when {
                primeNumbers[inputNumber] -> {
//                isPrime(inputNumber) -> {
                    var isPythagoreanPrimes = false
                    for (i in 1..inputNumber) {
                        when (4 * i + 1) {
                            inputNumber -> {
                                isPythagoreanPrimes = true
                                break
                            }
                        }
                    }
                    when {
                        isPythagoreanPrimes -> println("YES")
                        else -> println("NO")
                    }
                }
                else -> println("NO")
            }
        }
    }
    println("$time ms")
}

fun isPrime(number: Int): Boolean {
    return when (number) {
        0,1 -> false
        2 -> true
        else -> (3 until number).none { number % it == 0 }
    }
}

fun allPrimeNumbersTo(number: Int): Array<Boolean> {
    val primeNumbers = Array(number) { true }
    primeNumbers[0] = false
    primeNumbers[1] = false
    when {
        number <= 2 -> return primeNumbers
    }
    for (i in 2 until sqrt(number.toDouble()).toInt()) {
        if (primeNumbers[i]) {
            for (j in (i*i) until number step i) {
                primeNumbers[j] = false
            }
        }
    }
    return primeNumbers
}