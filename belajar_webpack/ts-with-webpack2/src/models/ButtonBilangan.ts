import Button from "./Button";
import { ButtonType } from "./ButtonType";

export default class ButtonBilangan extends Button {
  constructor(domId: string, value: string) {
    super(domId, ButtonType.Bilangan, value)
  }
}