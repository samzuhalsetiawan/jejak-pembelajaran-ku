const fs = require("fs");

const ambilData = path => {
    if (fs.existsSync(path)) {
        return JSON.parse(fs.readFileSync(path, "utf-8"));
    } else {
        return false;
    }
}

module.exports = {ambilData}