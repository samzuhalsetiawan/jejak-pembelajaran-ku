const fs = require("fs");
const constanta = require("../constanta/constanta");

const listData = () => {
    const data = fs.readFileSync(constanta.filePath, "utf-8");
    return JSON.parse(data);
}

module.exports = {listData}