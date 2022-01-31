fun main() {
    val myApp = Aplikasi()
    Activity.layoutInflater = LayoutInflater("myView")
    myApp.onCreate()
}

class LayoutInflater(val name: String)
class View(val text: String)
class ActivityBinding(val textView: View) {
    companion object {
        fun inflate(layoutInflater: LayoutInflater): ActivityBinding {
            return ActivityBinding(View(layoutInflater.name))
        }
    }
}
abstract class Activity {
    companion object {
        lateinit var layoutInflater: LayoutInflater
    }

    fun<T> lambat(initializer: () -> T): Lazy<T> {
        return object : Lazy<T> {

            var initialed = false

            override val value: T
                get() {
                    initialed = true
                    return initializer()
                }

            override fun isInitialized(): Boolean {
                return initialed
            }

        }
    }

}

class Aplikasi : Activity() {

//    private val binding = ActivityBinding.inflate(layoutInflater)
//    lateinit var binding: ActivityBinding
//    private val binding2 by lazy { ActivityBinding.inflate(layoutInflater) }
    private val binding by lambat { ActivityBinding.inflate(layoutInflater) }

    fun onCreate() {
//        binding = ActivityBinding.inflate(layoutInflater)
        println(binding.textView.text)
        println(lambat { null }.value)
    }
}