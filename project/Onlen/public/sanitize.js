const entities = {
    "<": "&#60;",
    ">": "&#62;",
    "\"": "&#34;",
    "!": "&#35;",
    "$": "&#36;",
    "%": "&#37;",
    "\'": "&#39;",
    "(": "&#40;",
    ")": "&#41;",
    "*": "&#42;",
    "#": "&#35;",
    "/": "&#47;",
    "\\": "&#92;",
    "&": "&#38;",
    "=": "&#61;",
    "?": "&#63;",
    "@": "&#64;",
    ":": "&#58;",
    "`": "&#96;",
    "{": "&#123;",
    "}": "&#125;",
    "[": "&#91;",
    "]": "&#93;",
    ",": "&#44;",
    ";": "&#59;"
}

function sanitize(str) {
    let newString = "";
    for (const huruf of str) {
        if (entities.hasOwnProperty(huruf)) {
            newString += entities[huruf];
        } else {
            newString += huruf;
        }
    }
    return newString;
}