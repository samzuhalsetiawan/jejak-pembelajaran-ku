const fs = require("fs");
const readLine = require("readline");

const rl = readLine.createInterface({
    input : process.stdin,
    output : process.stdout
});

console.log("\nSelamat Datang di app contact\n");

rl.question("Silahkan masukan nama anda:\n", nama => {
    rl.question("\nSilahkan masukan no HP anda:\n", noHP => {
        fs.mkdirSync("./contacts", {recursive : true});
        fs.writeFileSync("./contacts/contacts.json", `{\n"nama" : "${nama}",\n"noHP" : "${noHP}"\n}`);
        console.log("\ncontact anda berhasil dibuat\n");
        console.log(fs.readFileSync("./contacts/contacts.json", "utf-8"));
        rl.close();
    })
})