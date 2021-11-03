const http = require("http");
const fs = require("fs");

const renderHTML = (path, res) => {
    fs.readFile(path, "utf-8", (err, data) => {
        if (err) {
            res.writeHead(404);
        } else {
            res.write(data);
        }
        res.end();
    });
}

http.createServer((req, res) => {
    res.writeHead(200, {
        "Content-Type" : "text/html"
    });
    if (req.url == "/about") {
        renderHTML("./about.html", res);
    } else {
        renderHTML("./index.html", res);  
    }
    
}).listen(3000, () => {
    console.log("listening in server port 3000...");
})