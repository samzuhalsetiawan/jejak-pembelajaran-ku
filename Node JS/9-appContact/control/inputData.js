const consoleOut = require("./consoleOut");

const inputData = async () => {
    const nama = await consoleOut.pertanyaan("Siapa nama anda:\n");
    const noHP = await consoleOut.pertanyaan("Berapa nomor hp anda:\n");
    consoleOut.rl.close();
    const data = {nama, noHP};
    return data;
}

module.exports = {inputData}