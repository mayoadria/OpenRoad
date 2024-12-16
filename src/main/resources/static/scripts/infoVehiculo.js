// Elementos del DOM
const fechaRecogida = document.getElementById('fechaRecogida');
const horaRecogida = document.getElementById('horaRecogida');
const fechaEntrega = document.getElementById('fechaEntrega');
const diasReserva = document.getElementById('diasReserva');
const precioTotal = document.getElementById('precioTotal');

// Obtener precios dinámicamente desde el DOM
const obtenerPrecioPorDia = () => {
    const precioDiaElement = document.querySelector('[th\\:text="${vehicle.preuDia}"]');
    return parseFloat(precioDiaElement?.textContent?.trim()) || 0;
};

const obtenerPrecioFianza = () => {
    const precioFianzaElement = document.querySelector('[th\\:text="${vehicle.fianca}"]');
    return parseFloat(precioFianzaElement?.textContent?.trim()) || 0;
};

// Función para obtener la fecha actual en formato ISO (YYYY-MM-DD)
const obtenerFechaActual = () => {
    const hoy = new Date();
    const anio = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, '0');
    const dia = String(hoy.getDate()).padStart(2, '0');
    return `${anio}-${mes}-${dia}`;
};

// Función para inicializar las fechas predeterminadas
const establecerFechasIniciales = () => {
    const fechaActual = obtenerFechaActual();
    const manana = new Date();
    manana.setDate(manana.getDate() + 1);
    const fechaMananaISO = manana.toISOString().split('T')[0];

    fechaRecogida.value = fechaActual;
    fechaRecogida.min = fechaActual;

    fechaEntrega.value = fechaMananaISO;
    fechaEntrega.min = fechaMananaISO;
};

// Función para calcular la diferencia en días (inclusivos)
const calcularDias = (inicio, fin) => {
    const fechaInicio = new Date(inicio);
    const fechaFin = new Date(fin);
    if (!isNaN(fechaInicio) && !isNaN(fechaFin) && fechaFin >= fechaInicio) {
        const diferenciaMs = fechaFin - fechaInicio;
        return Math.ceil(diferenciaMs / (1000 * 60 * 60 * 24)) + 1; // Días inclusivos
    }
    return 0;
};

// Función para actualizar el precio total y los días de reserva
const actualizarPrecioYDias = () => {
    const precioPorDia = obtenerPrecioPorDia();
    const precioFianza = obtenerPrecioFianza();

    const inicio = fechaRecogida.value;
    const fin = fechaEntrega.value;

    const dias = calcularDias(inicio, fin);

    // Actualizar campos del DOM
    diasReserva.textContent = dias;
    if (dias > 0) {
        const total = precioPorDia * precioFianza * dias;
        precioTotal.textContent = `${total.toFixed(2)} €`;
    } else {
        precioTotal.textContent = '0 €';
    }
};

// Escuchar eventos
fechaRecogida.addEventListener('input', () => {
    const nuevaFechaEntregaMin = new Date(fechaRecogida.value);
    nuevaFechaEntregaMin.setDate(nuevaFechaEntregaMin.getDate() + 1);
    fechaEntrega.min = nuevaFechaEntregaMin.toISOString().split('T')[0];

    actualizarPrecioYDias();
});

fechaEntrega.addEventListener('input', actualizarPrecioYDias);

// Inicialización al cargar la página
window.addEventListener('DOMContentLoaded', () => {
    establecerFechasIniciales();
    actualizarPrecioYDias();
});
