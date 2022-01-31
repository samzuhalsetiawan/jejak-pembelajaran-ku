import kotlin.reflect.KProperty

class AplikasiKu: Activity() {

//    private val binding = ActivityBinding.inflate(layoutInflater)
//    private lateinit var binding: ActivityBinding
//    private val binding by lazy {
//        println("Properti ini di initial secara malas")
//        ActivityBinding.inflate(layoutInflater)
//    }
    private val binding by malas {
        println("Properti ini di initial secara malas")
        ActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate() {
//        binding = ActivityBinding.inflate(layoutInflater)
        println(binding.textView.text)
        println(binding.textView.text)
        println(binding.textView.text)
    }
}

fun main() {
    Activity.startActivity(AplikasiKu())
}