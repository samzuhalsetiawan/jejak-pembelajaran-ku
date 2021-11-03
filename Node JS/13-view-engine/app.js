const express = require("express");
const app = express();
const crud = require("./controller/crud");

const port = 3000;

app.set("views", "./public");
app.set("view engine", "ejs");

app.use(express.static("public"));
app.use(express.urlencoded({extended : true}));

app.get("/about", (req, res) => {
    res.render("main", {layout: "about"});
});

app.get("/home", (req, res) => {
    res.render("main", {layout: "home"});
});

app.get("/contact", (req, res) => {
    let data = crud.ambilData("contact.json");
    if (req.query.nama) {
        data = data.filter(e => e.nama.toLowerCase() == req.query.nama.toLowerCase());
    } else if (req.query.del) {
        res.send(crud.hapusData(req.query.del, data));
    }
    res.render("main", {
        layout: "contact",
        data
    });
});

app.get("/tambah-data", (req, res) => {
    res.render("main", {layout: "tambah-data"});
})

app.post("/tambah-data", (req, res) => {
    crud.tambahData(req.body, "contact.json");
    res.redirect("/contact");
})


app.listen(port, () => {
    console.log(`listening in port ${port}...`);
});