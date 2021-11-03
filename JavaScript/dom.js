const a = document.getElementById('a');
const b = a.getElementsByClassName('p2')[0];
const c = document.createElement('p');
const d = document.createTextNode('Ini Paragraf');
c.appendChild(d);
a.insertBefore(c, b);

const f = a.getElementsByTagName('a')[0];
a.removeChild(f);

const g = document.createElement('h1');
const h = document.createTextNode('Selamat');
g.appendChild(h);
a.replaceChild(g, b);