const { MongoClient, ObjectId } = require("mongodb");
const dbsName = "mahasiswa";
const collect = "datamahasiswa";
const uri = "mongodb://127.0.0.1:27017/";
const client = new MongoClient(uri, {
  useNewUrlParser : true,
  useUnifiedTopology : true
});

module.exports = { client, dbsName, collect, ObjectId }
