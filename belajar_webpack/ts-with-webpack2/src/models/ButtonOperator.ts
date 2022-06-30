import Button from "./Button";
import { ButtonType } from "./ButtonType";

export default class ButtonOperator extends Button {
  constructor(domId: string, value: string) {
    super(domId, ButtonType.Operator, value)
  }
}