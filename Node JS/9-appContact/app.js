const fs = require("fs");
const constanta = require("./constanta/constanta");
const inputData = require("./control/inputData");
const submitData = require("./control/submitData");

if (!fs.existsSync(constanta.dirPath)) fs.mkdirSync(constanta.dirPath);
if (!fs.existsSync(constanta.filePath)) fs.writeFileSync(constanta.filePath, `[]`);

const main = async () => {
    const newContact = await inputData.inputData();
    submitData.submitData(newContact);
}

main();