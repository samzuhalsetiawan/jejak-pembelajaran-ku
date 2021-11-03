const express = require("express");
const app = express();
const port = process.env.PORT || 3000;
const { v4 : uuidV4 } = require("uuid");

app.use(express.static("public"));
app.set("view engine", "ejs");

app.get("/", (req, res) => {
    res.render("index", { nama : "Sam Zuhal Setiawan", id : uuidV4()});
});

app.listen(port, () => {
    console.log("Server is Running");
});