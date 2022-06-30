import Button from "../models/Button"
import { ButtonType } from "../models/ButtonType"

export default class DisplayManager {
  public static currentCalc: string = ""
  private static currentButtonType: ButtonType
  private static displayEl: HTMLInputElement = document.getElementById("display") as HTMLInputElement
  public static addCalc(button: Button) {
    switch (button.type) {
      case ButtonType.Bilangan:
        this.currentCalc += button.value
        this.currentButtonType = button.type
        this.displayEl.value = this.currentCalc
        break;

      case ButtonType.Operator:
        if (this.currentButtonType != ButtonType.Bilangan) break
        this.currentCalc += button.value
        this.displayEl.value = this.currentCalc
        break;

      case ButtonType.Total:
        this.displayEl.value = (new Function("return " + this.currentCalc + ";"))()
        this.currentCalc = ""
        break;
    }
  }
} 