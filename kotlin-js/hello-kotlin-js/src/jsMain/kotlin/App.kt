import kotlinx.browser.document
import kotlinx.html.InputType
import kotlinx.html.dom.append
import kotlinx.html.js.div
import kotlinx.html.js.h3
import kotlinx.html.js.input
import kotlinx.html.js.kbd
import kotlinx.html.js.label
import kotlinx.html.js.section
import kotlinx.html.style
import kotlinx.html.svg
import org.w3c.dom.HTMLButtonElement

fun main() {
    val button = document.querySelector(".container > button") as? HTMLButtonElement
    val title = document.getElementById("title")

    var count = 0
    button?.addEventListener(type = "click", callback = { event ->
        count++
        title?.textContent = "Sam Zuhal Setiawan $count"
    })

    button?.classList?.add("btn", "btn-primary")

    document.body?.append {
        section {
            style = "height: 100vh; width: 100vw; display: flex; align-items: center; justify-content: center;"
            label("input") {
                input(
                    type = InputType.text,
                    classes = "search",
                ) {
                    placeholder = "Search"
                }
                kbd("kbd kdb-sm") {
                    +"⌘"
                }
                kbd("kbd kdb-sm") {
                    +"K"
                }
            }
        }
    }

}