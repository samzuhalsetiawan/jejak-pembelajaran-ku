function createElement(element) {
  const template = document.createElement("template");
  template.innerHTML = element.trim();
  return template.content.firstElementChild.cloneNode(true);
}

function navBar() {
  return createElement(/*html*/`
    <div class="nav-bar">
      <ul>
        <li>Home</li>
        <li>About</li>
        <li>Settings</li>
      </ul>
    </div>
  `)
}

function bigTron(images) {
  const element = createElement(/*html*/`
    <div class="big-tron">
      <div class="slider"></div>
      <div class="quotes">${"Anggap aja ini qoutes"}</div>
    </div>
  `);

  for (const image in images) {
    const img = document.createElement("img");
    img.src = images[image]
    element.querySelector(".slider").appendChild(img);
  }

  return element;
}

document.body.appendChild(navBar());
document.body.appendChild(bigTron(
  ["./img/2.jpg","./img/0.jpg", "./img/1.jpg", "./img/2.jpg"]
));

let counter = 0;
const imageWidth = document.querySelector(".slider img").clientWidth;
const slider = document.querySelector(".slider");
setInterval(() => {
  counter++;
  console.log(counter);
  if (counter == 4) {
    console.log("end");
    counter = 0;
    slider.style.transition = 'unset';
    slider.style.transform = `translateX(-${counter * imageWidth}px)`;
  } else {
    slider.style.transition = '0.5s';
    slider.style.transform = `translateX(-${counter * imageWidth}px)`;
  }
  
}, 3000);