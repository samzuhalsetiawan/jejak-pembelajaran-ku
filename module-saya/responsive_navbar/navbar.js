const buttonMobileNav = document.querySelector('.mobile-toggle-menu');
const ulNavbarMain = document.querySelector('.primary-navigation');

function toogleMobileNav() {
    const visibility = ulNavbarMain.getAttribute("data-visible");
    
    if (visibility == "false") {
        ulNavbarMain.setAttribute("data-visible", true);
        buttonMobileNav.setAttribute("aria-expanded", true);
    } else if (visibility == "true") {
        ulNavbarMain.setAttribute("data-visible", false);
        buttonMobileNav.setAttribute("aria-expanded", false);
    }
}

buttonMobileNav.addEventListener('click', toogleMobileNav);