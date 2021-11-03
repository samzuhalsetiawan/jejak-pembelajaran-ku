const fs = require("fs");
const yargs = require("yargs");

const constanta = require("./constanta/constanta");
const submitData = require("./control/submitData");
const listData = require("./control/listData");

if (!fs.existsSync(constanta.dirPath)) fs.mkdirSync(constanta.dirPath);
if (!fs.existsSync(constanta.filePath)) fs.writeFileSync(constanta.filePath, `[]`);

const main = async (data) => {
    submitData.submitData(data);
    console.log("data berhasil ditambahkan");

}

const command = ["add", "list", "remove"];

if (yargs.argv._.length != 1) {
    console.log("masukan perintah yg benar");
} else {
    if (!command.includes(yargs.argv._[0])) {
        console.log(`tidak ada perintah ${yargs.argv._[0]}`);
    }
}

yargs.command("add", "masukan nama", {
    nama : {
        describe : "masukan nama",
        type : "string",
        demandOption : true
    },
    noHP : {
        describe : "masukan noHP",
        type : "string",
        demandOption : true
    },
}, (argv) => {
    const data = {
        nama : argv.nama,
        noHP : argv.noHP
    };
    main(data);
}).demandCommand();

yargs.command({
    command : "list",
    describe : "menampilkan isi contact",
    handler() {
        const data = listData.listData();
        console.log("list contact :");
        data.forEach((value, index) => {
            console.log(`${index+1}. ${value.nama} - ${value.noHP}`);
        });
    }
});

yargs.command({
    command : "remove",
    describe : "menghapus isi contact",
    builder : {
        nama : {
            describe : "masukan nama",
            type : "string",
            demandOption : true
        }
    },
    handler(argv) {
        const data = listData.listData();
        data.forEach((value, index) => {
            if (value.nama == argv.nama) {
                data.splice(index, 1);
            }
        });
        fs.writeFileSync(constanta.filePath, JSON.stringify(data));
        console.log("data berhasil dihapus");
    }
});

yargs.parse();