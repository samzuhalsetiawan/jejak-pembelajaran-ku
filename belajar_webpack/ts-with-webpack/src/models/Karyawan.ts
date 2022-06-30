import Human from "./Human";

export default class Karyawan extends Human {
  public job: string
  constructor(name: string, age: number, job: string) {
    super(name, age)
    this.job = job
  }
}