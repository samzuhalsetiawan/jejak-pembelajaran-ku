fun main() {
//    completenya => val jumlahkanDenganLambda: (Int, Int) -> Int
    val jumlahkanDenganLambda = { nilai1: Int, nilai2: Int ->
        nilai1 + nilai2
    }

    val hasil = jumlahkanDenganLambda(1,1)
    println(hasil)
}