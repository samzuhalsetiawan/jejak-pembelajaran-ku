const fs = require('fs');

// const data = new Uint8Array(Buffer.from(`
//     const nama = "sam zuhal";

//     const sayNama = nama => "nama saya " + nama;

//     console.log(sayNama(nama));
// `));

// console.log(data);

// fs.writeFile('data.js', data, (err) => {
//   if (err) throw err;
//   console.log('The file has been saved!');
// });

// fs.mkdir('./nyobaMkDir' , {recursive : true}, err => {
//     if(err)console.log("not"); else {console.log("ok");
//     fs.writeFileSync("./nyobaMkDir/teks2.txt", "hello world");}
// });

// console.log(fs.readFile("./nyobaMkDir/teks5.txt", "ascii", (err, data) => {
//     console.log(err, data);
// }));

const readline = require('readline');


const rl = readline.createInterface({
    input : process.stdin,
    output : process.stdout
});

// cara 1
rl.question("Siapa nama anda?\n", (nama) => {
    console.log(`hai ${nama}`);
    rl.question("Berapa nomor hp anda?\n", (hape) => {
        console.log(`no hp anda: ${hape}`);
        rl.close();
    })
})

// cara 2
// async function ulangi() {
//     let myPromise = new Promise((myResolve)=>{
//         rl.question("siapa namamu?\n", nama => {
//             console.log(`hai ${nama}`);
//             // rl.close();
//             myResolve();
//         })
//     });
//     await myPromise;
//     let myPromise2 = new Promise((myResolve)=>{
//         rl.question("berapa nomor hpmu?\n", hp => {
//             console.log(`no hp mu ${hp}`);
//             rl.close();
//             myResolve("ok");
//         })
//     });
//     await myPromise2.then(value => console.log(value));
// }

// ulangi();

// const values = ['lorem ipsum', 'dolor sit amet'];
// const rln = readline.createInterface(process.stdin);
// function debounce(func, timeout = 300){
//     let timer;
//     return (...args) => {
//       clearTimeout(timer);
//       timer = setTimeout(() => { func.apply(this, args); }, timeout);
//     };
//   }
// const showResults = debounce(() => {
//   console.log(
//     '\n',
//     values.filter((val) => val.startsWith(rln.line)).join(' ')
//   );
// }, 300);
// process.stdin.on('keypress', (c, k) => {
//   showResults();
// });
