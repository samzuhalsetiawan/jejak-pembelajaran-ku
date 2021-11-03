const express = require("express");
const app = express();
const fs = require("fs");

const controller = require("./controller");

const port = 3000;

app.get("/About/:no", (req,res) => {
    const data = controller.ambilData("./data.json");
    if (data[req.params.no]) {
        res.status(200);
        res.type("json");
        res.json(data[req.params.no]);
    } else {
        res.status(404);
        res.send("data not found");
    }
})

app.get("/tes", (req,res) => {
    const data = fs.readFileSync("./client/index.html", "utf-8");
    if (data) {
        res.status(200);
        res.type("html");
        res.send(data);
    } else {
        res.status(404);
        res.send("data not found");
    }
})

app.use((req,res) => {
    res.status(404);
    res.send("page not found");
})

app.listen(port, () => {
    console.log(`listening in port ${port}...`);
})