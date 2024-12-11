// Inicializar el mapa
let map;
let marker;

// Crear y configurar el mapa al cargar la página
function inicializarMapa() {
    map = L.map('map').setView([40.416775, -3.703790], 13); // Centrado inicial en Madrid

    // Añadir el tile layer de OpenStreetMap
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);
}

// Función para buscar la dirección ingresada
function buscarDireccion() {
    const direccion = document.getElementById('direccion').value;
    const mensajeError = document.getElementById('error');

    // Realizar la consulta a Nominatim
    fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${direccion}`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                const lat = data[0].lat;
                const lon = data[0].lon;

                // Mover el mapa al marcador
                map.setView([lat, lon], 15);

                // Actualizar o agregar el marcador
                if (marker) {
                    marker.setLatLng([lat, lon]);
                } else {
                    marker = L.marker([lat, lon]).addTo(map);
                }

                // Ocultar el mensaje de error si la búsqueda fue exitosa
                mensajeError.style.display = 'none';
            } else {
                // Mostrar mensaje de error si no se encuentra la dirección
                mensajeError.style.display = 'block';
                mensajeError.textContent = 'No se pudo encontrar la dirección.';
            }
        })
        .catch(() => {
            // Manejar errores en la conexión o consulta
            mensajeError.style.display = 'block';
            mensajeError.textContent = 'Hubo un error al buscar la dirección. Inténtalo de nuevo.';
        });
}

// Inicializar el mapa al cargar la página
document.addEventListener('DOMContentLoaded', () => {
    inicializarMapa();

    // Asociar el evento de clic al botón de buscar
    document.getElementById('buscar').addEventListener('click', buscarDireccion);
});