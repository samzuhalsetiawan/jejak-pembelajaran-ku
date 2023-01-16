package belajar_coroutine

import kotlinx.coroutines.*

class LatihanRetrofit {

    fun main() {
        val job = CoroutineScope(Dispatchers.IO).launch {
            supervisorScope {
                val response = Api.jsonPlaceholder.getTodos()

                if (response.isSuccessful && response.body() != null) {
                    println(response.body())
                }
            }
        }
        runBlocking {
            job.join()
        }
    }
}