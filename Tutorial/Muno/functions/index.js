const functions = require("firebase-functions");

// Create and Deploy Your First Cloud Functions
// https://firebase.google.com/docs/functions/write-firebase-functions

const express = require("express");
const fs = require("fs");
const app = express();

const port = 3000;

app.use(express.static("public"));
app.use(express.urlencoded({extended : true}));
app.set("views", "./views");
app.set("view engine", "ejs");

app.get("/muno", (req, res) => {
    res.render("main-menu/main-menu");
});

app.get("/svg/:nama", (req, res) => {
    fs.readFile(`views/templates/svg-${req.params.nama}.html`, {encoding: "utf-8"}, (err, data) => {
        if (err) throw err;
        res.send(data);
    });
});

app.get("/data/:bank/:data", (req, res) => {
    fs.readFile("json/data.json", {encoding: "utf-8"}, (err, result) => {
        if (err) throw err;
        const data = JSON.parse(result);
        res.json(data[req.params.bank][req.params.data]);
    });
});

app.get("/text/:teks", (req, res) => {
    switch (req.params.teks) {
        case "c1":
            res.send(`<h1>MUno</h1><h2>Mulai</h2>`);
            break;
        case "c2":
            res.send(`<h1>MUno</h1><h2>Pengaturan</h2>`);
            break;
        case "c3":
            res.send(`<h1>MUno</h1><h2>Tentang Game</h2>`);
            break;
        case "testing":
            res.send(`testing`);
            break;
    }
});

app.get("/form-card", (req, res) => {
    fs.readFile("views/templates/form-card.html", {encoding: "utf-8"}, (err, data) => {
        if (err) throw err;
        res.send(data);
    });
});

app.post("/lobby", (req, res) => {
    console.log(req.body);
    res.render("lobby/lobby");
});

exports.app = functions.https.onRequest(app);
