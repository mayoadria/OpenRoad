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
    fechaRecogida.value = fechaActual; // Fecha actual como valor predeterminado
    fechaEntrega.value = fechaActual; // Fecha actual como valor predeterminado
    fechaRecogida.min = fechaActual; // Fecha mínima es hoy para recogida
    fechaEntrega.min = fechaActual; // Fecha mínima es hoy para entrega
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
    diasReserva.textContent = dias > 0 ? dias : '0';
    precioTotal.textContent = dias > 0 ? `${dias * precioPorDia + precioSeguro} €` : `${precioSeguro} €`;
};

// Escuchar eventos
fechaRecogida.addEventListener('input', () => {
    limitarHoraRecogida(); // Limita las horas si es necesario
    fechaEntrega.min = fechaRecogida.value; // Asegura que la fecha de entrega no sea anterior
    actualizarPrecio();
});

fechaEntrega.addEventListener('input', actualizarPrecio);
horaRecogida.addEventListener('input', limitarHoraRecogida);

// Inicialización al cargar la página
window.addEventListener('DOMContentLoaded', () => {
    establecerFechaHoy();
    limitarHoraRecogida();
    actualizarPrecio();
});
