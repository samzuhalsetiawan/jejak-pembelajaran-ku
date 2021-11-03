class Button: ButtonInterface {

    override var buttonName: String = "nama button"

    override fun onClick() {
        println("$buttonName clicked")
    }

    override fun onTouch() {
        println("$buttonName touched")
    }

}

interface ButtonInterface {
    var buttonName: String

    fun onClick()
    fun onTouch()
}

fun main() {
    val button = Button()

    println(button.buttonName)
    button.buttonName = "lolo"
    println(button.buttonName)

    button.onClick()
}