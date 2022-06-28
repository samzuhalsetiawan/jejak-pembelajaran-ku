const express = require("express");
const app = express();

const secretKey = "RAHASIA";
const PORT = 3001;

app.use(express.json()) // for parsing application/json
app.use(express.urlencoded({ extended: true })) // for parsing application/x-www-form-urlencoded

app.use(express.static("public"));

app.listen(PORT);