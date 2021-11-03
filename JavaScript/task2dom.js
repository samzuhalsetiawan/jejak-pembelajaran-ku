const selectInput = document.getElementsByTagName('input');

let valueMerah = 255;
let valueHijau = 255;
let valueBiru = 255;

selectInput[0].addEventListener('mousemove', function() {
	valueMerah = selectInput[0].value;
});

selectInput[1].addEventListener('mousemove', function() {
	valueHijau = selectInput[1].value;
});

selectInput[2].addEventListener('mousemove', function() {
	valueBiru = selectInput[2].value;
});

const selectContainer = document.querySelector('.containerSettings');

selectContainer.addEventListener('mousemove', function() {
	document.body.style.backgroundColor = 'rgb('+ valueMerah + ',' + valueHijau + ',' + valueBiru + ')';
});

const selectButtonMulaiGame = document.querySelector('.containerSettings ul li:last-child');
const selectKotak = document.querySelector('.mainGame .target');

function xPos(){
	 let xPos = 1316*Math.round(Math.random()*10)/10;
	 return xPos;	
}
function yPos(){
	 let yPos = 600*Math.round(Math.random()*10)/10;
	 return yPos;	
}

const selectScoreH1 = document.querySelector('.mainGame .score h1');
const selectScore = document.querySelector('.mainGame .score');
let score = 0;

selectButtonMulaiGame.addEventListener('click', function() {
	selectContainer.style.display = 'none';
	selectKotak.style.display = 'inline-block';
	selectKotak.style.height = '50px';
	selectKotak.style.transform = 'translate(' + xPos() +'px ,' + yPos() + 'px)';
	selectScore.style.display = 'inline-block';
	selectScore.style.transform = 'translate(0,0);'
});

selectKotak.addEventListener('transitionend', function() {
	selectKotak.style.transform = 'translate(' + xPos() +'px ,' + yPos() + 'px)';
});

selectKotak.addEventListener('click', function() {
	score += 5;
	selectScoreH1.innerHTML = score;
});