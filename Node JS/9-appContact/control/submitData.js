const fs = require("fs");
const constanta = require("../constanta/constanta");

const submitData = (data) => {
    const contactList = JSON.parse(fs.readFileSync(constanta.filePath, "utf8"));
    contactList.push(data);
    fs.writeFileSync(constanta.filePath, JSON.stringify(contactList));
}

module.exports = {submitData}