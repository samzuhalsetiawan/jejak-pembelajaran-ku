import kotlin.reflect.KProperty

abstract class Activity {
    abstract fun onCreate()
    companion object {
        lateinit var layoutInflater: LayoutInflater
        fun startActivity(activity: Activity) {
            layoutInflater = LayoutInflater(View("MyTextView"))
            activity.onCreate()
        }
    }
    fun<T> malas(initializer: () -> T): Lazy<T> {
       return object : Lazy<T> {
           private var hasBeenInitialed = false
           private var initialValue: T? = null
           override val value: T
               get() {
                   hasBeenInitialed = true
                   return initialValue ?: initializer().also {
                       initialValue = it
                   }
               }
           override fun isInitialized(): Boolean = hasBeenInitialed
       }
    }
}

class View(val text: String)
class LayoutInflater(val view: View)

abstract class ActivityBinding {
    abstract val textView: View
    companion object {
        fun inflate(layoutInflater: LayoutInflater): ActivityBinding {
            return object : ActivityBinding() {
                override val textView = layoutInflater.view
            }
        }
    }
}