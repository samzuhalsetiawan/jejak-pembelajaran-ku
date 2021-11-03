const express = require("express");
const https = require("https");
const fs = require("fs");
const { MongoClient } = require("mongodb");
const session = require("express-session");
const MongoStore = require("connect-mongo");
const uridb = "mongodb+srv://SamAdmin:W103v66t@cluster0.8agbl.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
const dbName = "ptac";
const app = express();
const port = process.env.PORT || 443;
const { sanitizeString } = require("./module-saya/html-sanitizer/sanitize");
const { json, query } = require("express");

const client = new MongoClient(uridb, {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

app.use(express.static("public"));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(session({
    secret: "54mZ m3ngg0k1l",
    resave: false,
    saveUninitialized: true,
    store: MongoStore.create({
        mongoUrl: uridb,
        dbName
    })
}));

const httpsOptions = {
    cert: fs.readFileSync("./Certificate/samzuhal.dev+4.pem"),
    key: fs.readFileSync("./Certificate/samzuhal.dev+4-key.pem")
}

const server = https.createServer(httpsOptions, app)

async function connectDB(callback, error = (err) => {
    console.log(err);
}) {
    try {
        await client.connect();
        const db = client.db(dbName);
        callback(db);
    } catch(err) {
        console.log(err);
        error(err);
    }
}

app.set("view engine", "ejs");

app.get("/", (req, res) => {
    if (req.session.login) {
        res.render("landing", { user: req.session.login });
    } else {
        res.render("landing", { user: null });
    }
});

app.get("/room/:role", (req, res) => {
    const data = []
    switch (req.params.role) {
        case "univ" :
            data.push("Dosen");
            data.push("Mahasiswa");
            break;
        case "sklh" :
            data.push("Guru");
            data.push("Siswa");
            break;
        default :
            data.push("pelajar");
    }

    if (req.session.login) {
        res.render("room", { roleRaw: JSON.stringify(data) });
    } else {
        res.redirect("/");
    }
})

app.get("/room", (req, res) => {
    if (req.session.login) {
        res.send("login");
    } else {
        res.send("not login");
    }
});

app.post("/login", (req, res) => {
    const body = {};
    body.username = (sanitizeString(req.body.username)).toLowerCase();
    body.password = sanitizeString(req.body.password);
    connectDB(async (db) => {
        try {
            const data = await db.collection("user").findOne({
                username: {
                    $in: [
                        body.username
                    ]
                }
            });
            if (data) {
                if (data.password == body.password) {
                    req.session.login = data.username[1];
                    res.send(data.username[0]);
                } else {
                    res.send("password tidak sesuai");
                }
            } else {
                res.send("user not found");
            }
        } catch (err) {
            console.log(err);
            res.send("database query error");
        } finally {
            client.close();
        }
    });
})

app.post("/buat-akun", (req, res) => {
    const body = {}
    body.username = [(sanitizeString(req.body.username)).toLowerCase(), sanitizeString(req.body.username)];
    body.password = sanitizeString(req.body.password);
    body.kelas = null;
    body.absensi = [];
    connectDB(async (db) => {
        try {
            const data = await db.collection("user").findOne({
                username: body.username
            });
            if (data) {
                res.send("username sudah ada");
            } else {
                await db.collection("user").insertOne(body);
                res.send("data berhasil ditambahkan");
            }
        } catch (err) {
            console.log(err);
            res.send("data gagal ditambahkan");
        } finally {
            client.close();
        }
    });
});

app.put("/room-data", (req, res) => {
    connectDB(async (db) => {
        try {
            const kelas = await db.collection("kelas").findOne({
                namaKelas: req.body.namaKelas,
                createdBy: req.session.login
            });
            if (kelas) {
                if (kelas.hasOwnProperty("absensiUsed")) {
                    res.send("Kelas pernah dibuat sebelumnya dan belum digunakan");
                    return;
                }
            }
            const data = await db.collection("user").updateOne({
                "username.1": req.session.login
            },
            {
                $set: {
                    kelas: {
                        namaKelas: req.body.namaKelas
                    }
                }
            });
            if (req.body.pass == false) {
                await db.collection("kelas").insertOne({ 
                    namaKelas: req.body.namaKelas,
                    createdBy: req.session.login
                });
            }
            if ( data.matchedCount < 1 ) {
                res.send("User tidak dikenali");
            } else {
                res.sendFile("room2.html", {
                    root: __dirname + "/views"
                });
            }
        } catch (err) {
            console.log(err);
            res.send("nama kelas gagal ditambahkan");
        } finally {
            client.close();
        }
    });
});

app.get("/absensi", (req, res) => {
    try {
        connectDB(async (db) => {
            const data = await db.collection("user").findOne({
                "username.1": req.session.login
            });
            res.send(data.absensi);
        });
    } catch (err) {
        res.send("Gagal terkoneksi dengan database");
        console.log(err);
    }
});

app.get("/buat-absensi", (req, res) => {
    res.sendFile("absensi.ejs", {
        root: __dirname + "/views"
    });
});

app.post("/post-absen", (req, res) => {
    const absen = req.body;
    try {
        connectDB(async (db) => {
            await db.collection("user").updateOne({
                username: {
                    $in: [req.session.login]
                }
            },
            {
                $push: {
                    absensi: absen
                }
            });
            res.send("data dimasukan");
        });
    } catch (err) {
        console.log(err);
    }
});

app.get("/link-kelas", (req, res) => {
    try {
        connectDB(async (db) => {
            await db.collection("user").updateOne({
                username: {
                    $in: [req.session.login]
                }
            },
            {
                $set: {
                    "kelas.absensi": req.query.opsi
                }
            });
            let absen = { namaAbsen: "tanpa absen" };
            if (req.query.opsi != "tanpa absen") {
                const dataAbsen = await db.collection("user").findOne({
                    username: {
                        $in: [req.session.login]
                    }
                },
                {
                    projection: {
                        absensi: {
                            $elemMatch: {
                                namaAbsen: req.query.opsi
                            }
                        }
                    }
                });
                absen = dataAbsen.absensi[0];
            }
            await db.collection("kelas").updateOne({
                namaKelas: req.query.kelas,
                createdBy: req.session.login
            },
            {
                $set: {
                    absensiUsed: absen
                }
            });
        });
    } catch (err) {
        console.log(err);
    }
    res.sendFile("link-kelas.html", {
        root: __dirname + "/views"
    });
})

server.listen(port, () => {
    console.log(`Server is Listening in port: ${port}`);
})