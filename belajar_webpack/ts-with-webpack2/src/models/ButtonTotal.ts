import Button from "./Button";
import { ButtonType } from "./ButtonType";

export default class ButtonTotal extends Button {
  constructor(domId: string, value: string) {
    super(domId, ButtonType.Total, value)
  }
}