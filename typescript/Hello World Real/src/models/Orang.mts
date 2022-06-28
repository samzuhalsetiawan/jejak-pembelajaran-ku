export default class Orang {
  public hobby: string
  private sukaGame: boolean
  constructor(hobby: string, sukaGame: boolean) {
    this.hobby = hobby
    this.sukaGame = sukaGame
  }
  public isSukaGame() {
    return this.sukaGame
  }
}