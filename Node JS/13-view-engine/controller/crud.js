const fs = require("fs");

const ambilData = (file) => JSON.parse(fs.readFileSync(`data/${file}`, "utf-8"));

const tambahData = (newData, file) => {
    const data = ambilData(file);
    data.push(newData);
    fs.writeFileSync(`data/${file}`, JSON.stringify(data));
}

const hapusData = (key, file) => {
    const index = file.indexOf(file.find(e => e.nama.toLowerCase()==key.toLowerCase()));
    const newData = file.filter(e => file.indexOf(e) != index);
    fs.writeFileSync('data/contact.json', JSON.stringify(newData));
    return newData;
}

module.exports = {ambilData, tambahData, hapusData}