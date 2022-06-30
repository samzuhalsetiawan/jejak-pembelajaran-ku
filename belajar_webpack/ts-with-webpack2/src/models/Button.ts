import DisplayManager from "../manager/DisplayManager"
import { ButtonType } from "./ButtonType"

export default abstract class Button {
  public readonly domId: string
  public readonly domElement: HTMLElement
  public readonly type: ButtonType
  public readonly value: string
  constructor(domId: string, type: ButtonType, value: string) {
    this.domId = domId
    this.domElement = document.getElementById(domId)
    this.type = type
    this.value = value
    this.domElement.onclick = () => DisplayManager.addCalc(this)
  }
}