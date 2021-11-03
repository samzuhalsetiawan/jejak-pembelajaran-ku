
async function main() {
    const express = require("express");
    const { client, dbsName, collect, ObjectId } = require("./models/datamahasiswa");
    
    const app = express();
    const port = 3000;
    
    app.set("views", "views");
    app.set("view engine", "ejs");
    
    app.use(express.static("public"));
    app.use(express.urlencoded({extended : true}));
    app.use(express.json());
    
    app.get("/", (req, res) => {
        res.render("home");
    });
    
    
    app.get("/lihat-data", async (req, res) => {
        try {
            await client.connect();
            const db = client.db(dbsName);
            const data = await db.collection(collect).find().toArray();
            if (req.query.edit) {
                res.render("lihat-data", { edit : true, data });
            } else {
                res.render("lihat-data", { edit : false, data });
            }
        } catch (err) {
            if (err) throw err;
            res.status(404);
        }
    });

    app.get("/tambah-data/:comand/:id", async (req, res) => {
        try {
            if (req.params.comand == "edit") {
                await client.connect();
                const db = client.db(dbsName);
                const data = await db.collection(collect).findOne({ _id: ObjectId(`${req.params.id}`)});
                console.log(data);
                res.render("tambah-data", { data });
            } else if (req.params.comand == "hapus") {
                await client.connect();
                const db = client.db(dbsName);
                await db.collection(collect).deleteOne(
                    {
                        _id: ObjectId(`${req.params.id}`)
                    });
                res.redirect("/lihat-data");
            }
        } catch (err) {
                if (err) throw err;
                res.status(404);
        }
    });
    
    app.get("/tambah-data", (req, res) => {
        res.render("tambah-data", {data : false});
    });

    app.post("/tambah-data", async (req, res) => {
        try {
            await client.connect();
            const db = client.db(dbsName);
            if (req.body._id) {
                await db.collection(collect).updateOne({_id : req.body._id}, req.body);
            } else {
                await db.collection(collect).insertOne(req.body);
            }
            res.redirect("/lihat-data");
        } catch (err) {
            if (err) throw err;
            res.status(404);
        }
    })
    
    app.get("/detail-personal", (req, res) => {
        res.render("detail-personal");
    });
    
    app.listen(port, () => {
        console.log(`listening in http://localhost:${port}`);
    });
}

main();
