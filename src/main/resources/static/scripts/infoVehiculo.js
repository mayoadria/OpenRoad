// Precios constantes
const precioPorDia = 75;
const precioSeguro = 30;

// Elementos del DOM
const fechaRecogida = document.getElementById('fechaRecogida');
const horaRecogida = document.getElementById('horaRecogida');
const fechaEntrega = document.getElementById('fechaEntrega');
const diasReserva = document.getElementById('diasReserva');
const precioTotal = document.getElementById('precioTotal');

// Función para obtener la fecha actual en formato ISO (YYYY-MM-DD)
const obtenerFechaActual = () => {
    const hoy = new Date();
    const anio = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, '0'); // Meses de 0 a 11
    const dia = String(hoy.getDate()).padStart(2, '0');
    return `${anio}-${mes}-${dia}`;
};

// Función para inicializar las fechas predeterminadas
const establecerFechaHoy = () => {
    const fechaActual = obtenerFechaActual();
    const manana = new Date();
    manana.setDate(manana.getDate() + 1); // Suma 1 día para la fecha mínima de entrega
    const fechaMananaISO = manana.toISOString().split('T')[0];

    fechaRecogida.value = fechaActual; // Fecha actual como valor predeterminado
    fechaEntrega.value = fechaMananaISO; // Mínimo un día de diferencia para entrega
    fechaRecogida.min = fechaActual; // Fecha mínima es hoy para recogida
    fechaEntrega.min = fechaMananaISO; // Fecha mínima es mañana para entrega
};

// Función para limitar las horas disponibles en "Hora recogida"
const obtenerHoraActual = () => {
    const ahora = new Date();
    const horas = String(ahora.getHours()).padStart(2, '0');
    const minutos = String(ahora.getMinutes()).padStart(2, '0');
    return `${horas}:${minutos}`;
};

const limitarHoraRecogida = () => {
    const horaActual = obtenerHoraActual();
    const hoy = obtenerFechaActual();
    if (fechaRecogida.value === hoy) {
        // Si la fecha de recogida es hoy, limita las horas disponibles
        for (const opcion of horaRecogida.options) {
            if (opcion.value && opcion.value < horaActual) {
                opcion.disabled = true;
            } else {
                opcion.disabled = false;
            }
        }
    } else {
        // Si la fecha de recogida no es hoy, habilita todas las opciones
        for (const opcion of horaRecogida.options) {
            opcion.disabled = false;
        }
    }
};

// Función para calcular la diferencia en días entre dos fechas
const calcularDias = (inicio, fin) => {
    const fechaInicio = new Date(inicio);
    const fechaFin = new Date(fin);
    if (!isNaN(fechaInicio) && !isNaN(fechaFin) && fechaFin >= fechaInicio) {
        const diferenciaMs = fechaFin - fechaInicio;
        return Math.ceil(diferenciaMs / (1000 * 60 * 60 * 24)); // Convertir de ms a días
    }
    return 0; // Si las fechas no son válidas
};

// Función para actualizar el precio total
const actualizarPrecio = () => {
    const inicio = fechaRecogida.value;
    const fin = fechaEntrega.value;
    const dias = calcularDias(inicio, fin);

    // Validación para garantizar un día mínimo de reserva
    if (dias < 1) {
        const fechaMinEntrega = new Date(inicio);
        fechaMinEntrega.setDate(fechaMinEntrega.getDate() + 1); // Fecha mínima: día siguiente a la recogida
        fechaEntrega.value = fechaMinEntrega.toISOString().split('T')[0];
        diasReserva.textContent = 1; // Mínimo un día
        precioTotal.textContent = `${precioPorDia + precioSeguro} €`; // Precio para un día
    } else {
        diasReserva.textContent = dias;
        precioTotal.textContent = `${dias * precioPorDia + precioSeguro} €`;
    }
};

// Escuchar eventos
fechaRecogida.addEventListener('input', () => {
    const nuevaFechaEntregaMin = new Date(fechaRecogida.value);
    nuevaFechaEntregaMin.setDate(nuevaFechaEntregaMin.getDate() + 1); // Mínimo un día después de la recogida
    fechaEntrega.min = nuevaFechaEntregaMin.toISOString().split('T')[0];

    limitarHoraRecogida(); // Limita las horas si es necesario
    actualizarPrecio(); // Actualiza el precio
});

fechaEntrega.addEventListener('input', actualizarPrecio);
horaRecogida.addEventListener('input', limitarHoraRecogida);

// Inicialización al cargar la página
window.addEventListener('DOMContentLoaded', () => {
    establecerFechaHoy();
    limitarHoraRecogida();
    actualizarPrecio();
});

document.querySelector('.alquilarButton').addEventListener('click', () => {
    const precioTotal = document.getElementById('precioTotal').textContent; // Obtiene el total
    localStorage.setItem('totalReserva', precioTotal); // Guarda el total en localStorage
    window.location.href = 'pagaReserva.html'; // Redirige a la página de pago
});

//******************************************************//

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


