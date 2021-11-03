class getCoordinate {
    constructor(klienX, klienY, xAwal, yAwal) {
        this.xGerak = klienX;
        this.yGerak = klienY;
        this.perpindahanX = this.xGerak - xAwal;
        this.perpindahanY = this.yGerak - yAwal;
    }
}

class getNewLebar extends getCoordinate {
    constructor(klienX, klienY, lebarAwal, xAwal, yAwal) {
        super(klienX, klienY, xAwal, yAwal);
        this.lebarP = lebarAwal + this.perpindahanX;
        this.lebarN = lebarAwal + (this.perpindahanX * -1);
    }
}

class getNewTinggi extends getCoordinate {
    constructor(klienX, klienY, tinggiAwal, xAwal, yAwal) {
        super(klienX, klienY, xAwal, yAwal);
        this.tinggi = tinggiAwal + this.perpindahanY;
    }
}

function disableScrolling(){
    let x = window.scrollX;
    let y = window.scrollY;
    window.onscroll = () => window.scrollTo(x, y);
}

function enableScrolling(){
    window.onscroll = () => {};
}

export function resizeAble(element, options = {}) {
    let defaultOptions = {
        tinggiAtas: "10px"
    }
    if (!(options == undefined) || !(options == {})) {
        for (let key in options) {
            defaultOptions[key] = options[key];
        }
    }
    // device detection
    let isMobile = false;
    if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Mobi|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent) 
        || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0,4))) { 
        isMobile = true;
    }
    
    const borderPosition = ["east", "south-east", "south", "south-west", "west", "north"];
    
    element.style.position = "absolute";
    
    let opacity = "opacity: 0;";
    if (isMobile) {
        opacity = "";
    }
    const style = document.createElement("style");
    style.textContent = `
    html,
    body {
        overscroll-behavior-y: contain;
    }
    border {
        display: block;
        position: absolute;
        ${opacity}
        z-index: 99;
    }
    .border-east {
        top: 5px;
        right: 0;
        width: 5px;
        height: calc(100% - 10px);
        cursor: e-resize;
    }
    .border-south-east {
        right: 0;
        bottom: 0;
        width: 5px;
        height: 5px;
        cursor: se-resize;
    }
    .border-south {
        height: 5px;
        width: calc(100% - 10px);
        right: 5px;
        bottom: 0;
        cursor: s-resize;
    }
    .border-south-west {
        left: 0;
        bottom: 0;
        width: 5px;
        height: 5px;
        cursor: sw-resize;
    }
    .border-west {
        width: 5px;
        height: calc(100% - 10px);
        top: 5px;
        left: 0;
        cursor: w-resize;
    }
    .border-north {
        height: ${defaultOptions.tinggiAtas};
        width: calc(100% - 10px);
        top: 0;
        left: 5px;
        cursor: move;
    }
    `

    element.appendChild(style);

    for (let position of borderPosition) {
        const border = document.createElement("border");
        border.classList.add(`border-${position}`);
        function sentuhMulai(e) {
            const xAwal = e.touches[0].clientX;
            const yAwal = e.touches[0].clientY;
            borderEvent(e, xAwal, yAwal);
        }
        function mouseDown(e) {
            const xAwal = e.clientX;
            const yAwal = e.clientY;
            borderEvent(e, xAwal, yAwal);
        }
        function borderEvent (e, xAwal, yAwal) {
            disableScrolling();
            const lebarAwal = e.target.parentElement.offsetWidth;
            const tinggiAwal = e.target.parentElement.offsetHeight;
            const arah = borderPosition.filter( posisi => e.target.classList.contains(`border-${posisi}`));
            const rect = e.target.parentElement.getBoundingClientRect();
            let mouseGerak = null;
            let sentuhGerak = null;
            if (arah[0] == "east" || arah[0] == "west") {
                function gerak(klienX, klienY) {
                    const newLebar = new getNewLebar(klienX, klienY, lebarAwal, xAwal, yAwal);
                    if (arah[0] == "east") {
                        e.target.parentElement.style.width = `${newLebar.lebarP}px`;
                    } else if (arah[0] == "west") {
                        e.target.parentElement.style.left = `${rect.left + newLebar.perpindahanX}px`;
                        e.target.parentElement.style.width = `${newLebar.lebarN}px`;
                    }
                }
                mouseGerak = function (ev) {
                    gerak(ev.clientX, ev.clientY);
                }
                sentuhGerak = function(ev) {
                    gerak(ev.touches[0].clientX, ev.touches[0].clientY);
                }
            } else if (arah[0] == "south-east" || arah[0] == "south-west") {
                function gerak(klienX, klienY) {
                    const newLebar = new getNewLebar(klienX, klienY, lebarAwal, xAwal, yAwal);
                    const newTinggi = new getNewTinggi(klienX, klienY, tinggiAwal, xAwal, yAwal);
                    e.target.parentElement.style.height = `${newTinggi.tinggi}px`;
                    if (arah[0] == "south-east") {
                        e.target.parentElement.style.width = `${newLebar.lebarP}px`;
                    } else if (arah[0] == "south-west") {
                        e.target.parentElement.style.width = `${newLebar.lebarN}px`;
                        e.target.parentElement.style.left = `${rect.left + newLebar.perpindahanX}px`;
                    }
                }
                mouseGerak = function (ev) {
                    gerak(ev.clientX, ev.clientY);
                }
                sentuhGerak = function (ev) {
                    gerak(ev.touches[0].clientX, ev.touches[0].clientY);
                }
            } else if (arah[0] == "south") {
                function gerak(klienX, klienY) {
                    const newTinggi = new getNewTinggi(klienX, klienY, tinggiAwal, xAwal, yAwal);
                    e.target.parentElement.style.height = `${newTinggi.tinggi}px`;
                }
                mouseGerak = function (ev) {
                    gerak(ev.clientX, ev.clientY);
                }
                sentuhGerak = function (ev) {
                    gerak(ev.touches[0].clientX, ev.touches[0].clientY);
                }
            } else if (arah[0] == "north") {
                function gerak(klienX, klienY) {
                    const coor = new getCoordinate(klienX, klienY, xAwal, yAwal);
                    e.target.parentElement.style.left = `${rect.left + coor.perpindahanX}px`;
                    e.target.parentElement.style.top = `${rect.top + coor.perpindahanY}px`;
                }
                mouseGerak = function (ev) {
                    gerak(ev.clientX, ev.clientY);
                }
                sentuhGerak = function (ev) {
                    gerak(ev.touches[0].clientX, ev.touches[0].clientY);
                }
            }
            document.addEventListener("mousemove", mouseGerak);
            document.addEventListener("touchmove", sentuhGerak);
            document.addEventListener("mouseup", function () {
                document.removeEventListener("mousemove", mouseGerak);
                enableScrolling();
            });
            document.addEventListener("touchend", function () {
                document.removeEventListener("touchmove", sentuhGerak);
                enableScrolling();
            });
        };
        border.addEventListener("mousedown", mouseDown);
        border.addEventListener("touchstart", sentuhMulai);
        element.appendChild(border);
    }

}