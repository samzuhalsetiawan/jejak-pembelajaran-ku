/**
 * @type {HTMLButtonElement}
 */
const btnGenerate1 = document.querySelector(".btn_generate1")
const btnGenerate2 = document.querySelector(".btn_generate2")
const btnGenerate3 = document.querySelector(".btn_generate3")
const btnDelete1 = document.querySelector(".delete1")
const btnDelete2 = document.querySelector(".delete2")
const btnDelete3 = document.querySelector(".delete3")
const btnAdd = document.querySelector(".btn_add")
/**
 * @type {HTMLInputElement}
 */
const btnCustom = document.querySelector("#custom_nickname")

/**
 * @type {HTMLDivElement}
 */
const divResultDisplay1 = document.querySelector(".nick1")
const divResultDisplay2 = document.querySelector(".nick2")
const divResultDisplay3 = document.querySelector(".nick3")

const charPool = "abcdefghijklmnopqrstuvwxyz"
const vowelCharPool = "aiueo"
const nonVowelCharPool = [...charPool].filter(char => ![...vowelCharPool].includes(char)).join("")
const MAX_NICKNAME_LENGTH = 4
const MIN_NICKNAME_LENGTH = 2


btnGenerate1.addEventListener("click", function(ev) {
    const prefix = document.querySelector(".prefix1").value
    const suffix = document.querySelector(".suffix1").value
    const nicname = generateNickname(prefix || "", suffix || "")
    divResultDisplay1.textContent = nicname
})
btnDelete1.addEventListener("click", function(ev) {
    divResultDisplay1.textContent = ""
})
btnGenerate2.addEventListener("click", function(ev) {
    const prefix = document.querySelector(".prefix2").value
    const suffix = document.querySelector(".suffix2").value
    const nicname = generateNickname(prefix || "", suffix || "")
    divResultDisplay2.textContent = nicname
})
btnDelete2.addEventListener("click", function(ev) {
    divResultDisplay2.textContent = ""
})
btnGenerate3.addEventListener("click", function(ev) {
    const prefix = document.querySelector(".prefix3").value
    const suffix = document.querySelector(".suffix3").value
    const nicname = generateNickname(prefix || "", suffix || "")
    divResultDisplay3.textContent = nicname
})
btnDelete3.addEventListener("click", function(ev) {
    divResultDisplay3.textContent = ""
})

btnAdd.addEventListener("click", function(ev) {
    const nickname = divResultDisplay1.textContent + divResultDisplay2.textContent + divResultDisplay3.textContent
    addToFavorite(nickname)
})

btnCustom.addEventListener("keydown", function(ev) {
    if (ev.key != "Enter") return
    const nickname = btnCustom.value
    btnCustom.value = ""
    addToFavorite(nickname)
})

/**
 * 
 * @param {string} nickname 
 */
function addToFavorite(nickname) {
    const div = document.createElement("div")
    div.textContent = nickname
    document.querySelector(".kandidat").appendChild(div)
}

/**
 * @param {string} prefix
 * @param {string} suffix  
 * @returns {string}
 */
function generateNickname(prefix, suffix) {
    let nicknameBuilderLength = getRandomNumber(MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH)
    let nicknameBuilder = ""
    if (prefix || suffix) nicknameBuilderLength = getRandomNumber(0, MAX_NICKNAME_LENGTH - 1)

    for (let i = 0; i < nicknameBuilderLength; i++) {
        if (nicknameBuilder.length != 0 && nonVowelCharPool.includes(nicknameBuilder.charAt(nicknameBuilder.length -1))) {
            nicknameBuilder += getRandomChar("vowel")
        } else {
            nicknameBuilder += getRandomChar()
        }
    }

    const nickname = `${prefix || ""}${nicknameBuilder}${suffix || ""}` 
    return nickname
}

/**
 * @param {"vowel" | "non-vowel" | "all"} type 
 * @returns {string}
 */
function getRandomChar(type = "all") {
    let pool = charPool
    if (type === "vowel") pool = vowelCharPool
    if (type === "non-vowel") pool = nonVowelCharPool
    const randomIndex = getRandomNumber(0, pool.length - 1)
    return pool.charAt(randomIndex)
}

/**
 * 
 * @param {number} min 
 * @param {number} max 
 * @returns {number}
 */
function getRandomNumber(min, max) {
    return Math.round((Math.random() * (max - min)) + min)
}

const slideValues = [1,0,0]
const tes = document.querySelectorAll('input[type="range"]')
tes.forEach((slide, key) => slide.value = (slideValues[key] * 100).toString())
tes.forEach((slide, key) => slide.addEventListener("input", function(ev) {
    updateValue(key, ev.target.valueAsNumber / 100)
}))
