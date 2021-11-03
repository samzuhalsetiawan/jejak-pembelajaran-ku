const http = require('http');

var options = {
    host: 'ipv4bot.whatismyipaddress.com',
    port: 80,
    path: '/'
};

http.get(options, function (res) {
    console.log("status: " + res.statusCode);

    res.on("data", function (chunk) {
        console.log("BODY: " + chunk);
    });
}).on('error', function (e) {
    console.log("error: " + e.message);
});