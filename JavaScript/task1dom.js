
function buatDanMasukin(namaTag, isinya, metode, parentOrSelector, indexParent) {
	let tag = document.createElement(namaTag);
	let nodeText = document.createTextNode(isinya);
	tag.appendChild(nodeText);
	let select = document.getElementsByTagName(parentOrSelector)[indexParent];
	if (metode == 1) {
		select = document.querySelector(parentOrSelector);	
	}
	select.appendChild(tag);
	return select;
}

buatDanMasukin('link', '', 0, 'head', 0);
const link = document.getElementsByTagName('link')[0];
link.setAttribute('rel', 'stylesheet');
link.setAttribute('href', '../CSS/galeri/reset.css');
buatDanMasukin('div', '', 0, 'body', 0);
buatDanMasukin('h1', 'Registrasi', 0, 'div', 0);
let selectDiv = document.getElementsByTagName('div')[0];
selectDiv.setAttribute('class', 'container');
let selectH1 = document.querySelector('.container h1');
selectH1.style.textAlign = 'center';
selectDiv.style.width = '600px';
selectDiv.style.border = '2px solid red';
selectDiv.style.marginLeft = 'auto';
selectDiv.style.marginRight = 'auto';
selectDiv.style.marginTop = '50px';
selectDiv.style.boxSizing = 'border-box';
selectH1.style.fontSize = '30px';
selectH1.style.fontFamily = 'arial';
selectH1.style.paddingTop = '20px';
selectH1.style.paddingBottom = '20px';
let createTag = document.createElement('ul');
let textnya = document.createTextNode('');
createTag.appendChild(textnya);
selectDiv.appendChild(createTag);
createTag = document.createElement('li');
textnya = document.createTextNode('Username');
createTag.appendChild(textnya);
let selectUl = document.querySelector('.container ul');
selectUl.appendChild(createTag);
selectUl.innerHTML = '<li><label for="username">Username</label><input type="text" name="username" id="username"></input></li><li><label for="password">Password</label><input type="text" name="password" id="password"></li>';
selectUl.style.textAlign = "center";
let selectLabel = document.getElementsByTagName('label');
selectLabel[0].style.display = 'block';
selectLabel[1].style.display = 'block';
createTag = document.createElement('div');
let selectLi = selectUl.getElementsByTagName('li');
let selectInput = selectUl.getElementsByTagName('input');
createTag.appendChild(selectLabel[0]);
createTag.appendChild(selectInput[0]);
selectLi[0].appendChild(createTag);
let selectDivUl = selectUl.getElementsByTagName('div');
selectDivUl[0].style.marginBottom = '10px';
createTag = document.createElement('li');
selectUl.appendChild(createTag);
createTag = document.createElement('button');
textnya = document.createTextNode('Submit');
createTag.appendChild(textnya);
selectLi[2].appendChild(createTag);
selectInput[1].style.marginBottom = '20px';
selectUl.style.marginBottom = '50px';
let selectBody = document.getElementsByTagName('body')[0];
selectBody.style.backgroundColor = '#AED6F1';
selectDiv.style.boxShadow = '0 0 12px 3px #3498DB';
selectDiv.style.backgroundColor = 'rgba(127, 179, 213, 45%)';