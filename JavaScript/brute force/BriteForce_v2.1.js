const possibleChar = "abcdefghijklmnopqrstuvwxyz";

function createPool(digit) {
  const pool = [...possibleChar].map((char, index) => {
    if (index == 0) {
      const arr = Array(digit).fill(index);
      arr[digit - 1] -= 1;
      return arr;
    }
    return Array(digit).fill(index);
  });
  pool.pop();
  return pool;
}

let pool = createPool(7);

async function doBruteForce(from, to, find) {
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
      if (updatedPass == find) {
        resolve(updatedPass);
        break;
      }
    }
    reject("Gagal ditemukan");
  });
}

async function splitBruteForce(find) {
  return new Promise((resolve, reject) => {
    for (const kombinasi of pool) {
      const from = [...kombinasi];
      const to = kombinasi.map(char => possibleChar[char + (char == -1 ? 2 : 1)]).join("");
      doBruteForce(kombinasi, to, find)
        .then(res => resolve(res))
        .catch(err => console.log("Failed"));
    }
  });
}

const start = Date.now();
splitBruteForce("windows").then(res => console.log(res));
const duration = Date.now() - start;
console.log("Program finnished in :" + duration);