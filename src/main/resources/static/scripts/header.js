let lastScrollY = window.scrollY; // Guarda la posición inicial del scroll

window.addEventListener('scroll', () => {
    const nav = document.querySelector('.nav-container');

    if (window.scrollY > lastScrollY) {
        // Si el usuario se desplaza hacia abajo, oculta el header
        nav.classList.add('hidden');
    } else {
        // Si el usuario se desplaza hacia arriba, muestra el header
        nav.classList.remove('hidden');
    }

    lastScrollY = window.scrollY; // Actualiza la posición del scroll
});
