const possibleChar = "abcdefghijklmnopqrstuvwxyz0123456789";

const start = Date.now();
let updatedPass = "";
let currentCombination = [0,0,0,0,0,0,0,-1];
while (updatedPass !== "w103v66t") {
  if (currentCombination[currentCombination.length - 1] != possibleChar.length - 1) {
    currentCombination[currentCombination.length - 1]++;
  } else {
    currentCombination[currentCombination.length - 1] = 0;
    let currentPointer = 1;
    while (true) {
      if (currentCombination.length - 1 - currentPointer < 0) {
        currentCombination.unshift(-1);
      }
      if (currentCombination[currentCombination.length - 1 - currentPointer] < possibleChar.length - 1) {
        currentCombination[currentCombination.length - 1 - currentPointer]++;
        break;
      } else {
        currentCombination[currentCombination.length - 1 - currentPointer] = 0;
        currentPointer++;
      }
    }
  }
  const result = currentCombination.reduce((acc, current) => acc + possibleChar[current], "");
  updatedPass = result;
}

console.log(updatedPass);
const duration = Date.now() - start;
console.log("Program finnished in :" + duration);