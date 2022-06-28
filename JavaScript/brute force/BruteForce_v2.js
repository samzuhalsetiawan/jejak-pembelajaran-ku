const possibleChar = "abcdefghijklmnopqrstuvwxyz";

async function doBruteForce(from, to) {
  return new Promise((resolve, reject) => {
    let updatedPass = "";
    let currentCombination = from;
    while (updatedPass !== to) {
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
      updatedPass = currentCombination.reduce((acc, current) => acc + possibleChar[current], "");
    }
    resolve(updatedPass);
    // reject("Gagal ditemukan");
  });
}

const start = Date.now();
doBruteForce([0,0,-1], "999999").then(res => console.log(res));
const duration = Date.now() - start;
console.log("Program finnished in :" + duration);