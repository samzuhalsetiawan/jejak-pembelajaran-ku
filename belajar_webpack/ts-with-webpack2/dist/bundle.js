/******/ (() => { // webpackBootstrap
/******/ 	"use strict";
/******/ 	var __webpack_modules__ = ({

/***/ "./src/manager/DisplayManager.ts":
/*!***************************************!*\
  !*** ./src/manager/DisplayManager.ts ***!
  \***************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (/* binding */ DisplayManager)
/* harmony export */ });
/* harmony import */ var _models_ButtonType__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../models/ButtonType */ "./src/models/ButtonType.ts");

class DisplayManager {
    static addCalc(button) {
        switch (button.type) {
            case _models_ButtonType__WEBPACK_IMPORTED_MODULE_0__.ButtonType.Bilangan:
                this.currentCalc += button.value;
                this.currentButtonType = button.type;
                this.displayEl.value = this.currentCalc;
                break;
            case _models_ButtonType__WEBPACK_IMPORTED_MODULE_0__.ButtonType.Operator:
                if (this.currentButtonType != _models_ButtonType__WEBPACK_IMPORTED_MODULE_0__.ButtonType.Bilangan)
                    break;
                this.currentCalc += button.value;
                this.displayEl.value = this.currentCalc;
                break;
            case _models_ButtonType__WEBPACK_IMPORTED_MODULE_0__.ButtonType.Total:
                this.displayEl.value = (new Function("return " + this.currentCalc + ";"))();
                this.currentCalc = "";
                break;
        }
    }
}
DisplayManager.currentCalc = "";
DisplayManager.displayEl = document.getElementById("display");


/***/ }),

/***/ "./src/models/Button.ts":
/*!******************************!*\
  !*** ./src/models/Button.ts ***!
  \******************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (/* binding */ Button)
/* harmony export */ });
/* harmony import */ var _manager_DisplayManager__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../manager/DisplayManager */ "./src/manager/DisplayManager.ts");

class Button {
    constructor(domId, type, value) {
        this.domId = domId;
        this.domElement = document.getElementById(domId);
        this.type = type;
        this.value = value;
        this.domElement.onclick = () => _manager_DisplayManager__WEBPACK_IMPORTED_MODULE_0__["default"].addCalc(this);
    }
}


/***/ }),

/***/ "./src/models/ButtonBilangan.ts":
/*!**************************************!*\
  !*** ./src/models/ButtonBilangan.ts ***!
  \**************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (/* binding */ ButtonBilangan)
/* harmony export */ });
/* harmony import */ var _Button__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./Button */ "./src/models/Button.ts");
/* harmony import */ var _ButtonType__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./ButtonType */ "./src/models/ButtonType.ts");


class ButtonBilangan extends _Button__WEBPACK_IMPORTED_MODULE_0__["default"] {
    constructor(domId, value) {
        super(domId, _ButtonType__WEBPACK_IMPORTED_MODULE_1__.ButtonType.Bilangan, value);
    }
}


/***/ }),

/***/ "./src/models/ButtonOperator.ts":
/*!**************************************!*\
  !*** ./src/models/ButtonOperator.ts ***!
  \**************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (/* binding */ ButtonOperator)
/* harmony export */ });
/* harmony import */ var _Button__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./Button */ "./src/models/Button.ts");
/* harmony import */ var _ButtonType__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./ButtonType */ "./src/models/ButtonType.ts");


class ButtonOperator extends _Button__WEBPACK_IMPORTED_MODULE_0__["default"] {
    constructor(domId, value) {
        super(domId, _ButtonType__WEBPACK_IMPORTED_MODULE_1__.ButtonType.Operator, value);
    }
}


/***/ }),

/***/ "./src/models/ButtonTotal.ts":
/*!***********************************!*\
  !*** ./src/models/ButtonTotal.ts ***!
  \***********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (/* binding */ ButtonTotal)
/* harmony export */ });
/* harmony import */ var _Button__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./Button */ "./src/models/Button.ts");
/* harmony import */ var _ButtonType__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./ButtonType */ "./src/models/ButtonType.ts");


class ButtonTotal extends _Button__WEBPACK_IMPORTED_MODULE_0__["default"] {
    constructor(domId, value) {
        super(domId, _ButtonType__WEBPACK_IMPORTED_MODULE_1__.ButtonType.Total, value);
    }
}


/***/ }),

/***/ "./src/models/ButtonType.ts":
/*!**********************************!*\
  !*** ./src/models/ButtonType.ts ***!
  \**********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "ButtonType": () => (/* binding */ ButtonType)
/* harmony export */ });
var ButtonType;
(function (ButtonType) {
    ButtonType[ButtonType["Bilangan"] = 0] = "Bilangan";
    ButtonType[ButtonType["Operator"] = 1] = "Operator";
    ButtonType[ButtonType["Total"] = 2] = "Total";
})(ButtonType || (ButtonType = {}));


/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/************************************************************************/
/******/ 	/* webpack/runtime/define property getters */
/******/ 	(() => {
/******/ 		// define getter functions for harmony exports
/******/ 		__webpack_require__.d = (exports, definition) => {
/******/ 			for(var key in definition) {
/******/ 				if(__webpack_require__.o(definition, key) && !__webpack_require__.o(exports, key)) {
/******/ 					Object.defineProperty(exports, key, { enumerable: true, get: definition[key] });
/******/ 				}
/******/ 			}
/******/ 		};
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/hasOwnProperty shorthand */
/******/ 	(() => {
/******/ 		__webpack_require__.o = (obj, prop) => (Object.prototype.hasOwnProperty.call(obj, prop))
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/make namespace object */
/******/ 	(() => {
/******/ 		// define __esModule on exports
/******/ 		__webpack_require__.r = (exports) => {
/******/ 			if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 				Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 			}
/******/ 			Object.defineProperty(exports, '__esModule', { value: true });
/******/ 		};
/******/ 	})();
/******/ 	
/************************************************************************/
var __webpack_exports__ = {};
// This entry need to be wrapped in an IIFE because it need to be isolated against other modules in the chunk.
(() => {
/*!**********************!*\
  !*** ./src/index.ts ***!
  \**********************/
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./models/ButtonBilangan */ "./src/models/ButtonBilangan.ts");
/* harmony import */ var _models_ButtonOperator__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./models/ButtonOperator */ "./src/models/ButtonOperator.ts");
/* harmony import */ var _models_ButtonTotal__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./models/ButtonTotal */ "./src/models/ButtonTotal.ts");



const satu = new _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__["default"]("satu", "1");
const dua = new _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__["default"]("dua", "2");
const tiga = new _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__["default"]("tiga", "3");
const empat = new _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__["default"]("empat", "4");
const lima = new _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__["default"]("lima", "5");
const enam = new _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__["default"]("enam", "6");
const tujuh = new _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__["default"]("tujuh", "7");
const delapan = new _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__["default"]("delapan", "8");
const sembilan = new _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__["default"]("sembilan", "9");
const nol = new _models_ButtonBilangan__WEBPACK_IMPORTED_MODULE_0__["default"]("nol", "0");
const tambah = new _models_ButtonOperator__WEBPACK_IMPORTED_MODULE_1__["default"]("tambah", "+");
const bagi = new _models_ButtonOperator__WEBPACK_IMPORTED_MODULE_1__["default"]("bagi", "/");
const kali = new _models_ButtonOperator__WEBPACK_IMPORTED_MODULE_1__["default"]("kali", "*");
const kurang = new _models_ButtonOperator__WEBPACK_IMPORTED_MODULE_1__["default"]("kurang", "-");
const total = new _models_ButtonTotal__WEBPACK_IMPORTED_MODULE_2__["default"]("total", "=");

})();

/******/ })()
;
//# sourceMappingURL=bundle.js.map