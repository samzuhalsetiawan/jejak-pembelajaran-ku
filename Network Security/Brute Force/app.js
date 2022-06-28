const express = require("express");
const app = express();

const secretKey = "RAHASIA";
const PORT = 3000;

app.use(express.json()) // for parsing application/json
app.use(express.urlencoded({ extended: true })) // for parsing application/x-www-form-urlencoded

app.use(express.static("public"));

app.post("/cek-pass", (req, res) => {
  const pass = req.body
  console.log(pass);
  res.json({ status: "Berhasil" });
});

app.listen(PORT);