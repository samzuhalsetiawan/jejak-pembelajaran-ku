import Bicara from "./Bicara";

export default abstract class Human implements Bicara {
  public name: string;
  public age: number;
  constructor(name: string, age: number) {
    this.name = name
    this.age = age
  }
  sayName(name: string): void {
    console.log(`Hello, ${name}`)
  }
  sayHelloTo(name: string): void {
    console.log(`Hello ${name}, My name is ${this.name}`)
  }
}