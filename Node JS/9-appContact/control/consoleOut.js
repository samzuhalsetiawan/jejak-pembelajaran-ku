const readline = require("readline");

const rl = readline.createInterface({
    input : process.stdin,
    output : process.stdout
});

const pertanyaan = (question) => {
    return new Promise((resolve)=>{
        rl.question(question, data => {
            resolve(data);
        })
    });
}

module.exports = {rl, pertanyaan}