// Elementos del DOM
const fechaRecogida = document.getElementById('fechaRecogida');
const horaRecogida = document.getElementById('horaRecogida');
const fechaEntrega = document.getElementById('fechaEntrega');
const diasReserva = document.getElementById('diasReserva');
const precioTotal = document.getElementById('precioTotal');



// Obtener precios dinámicamente desde el DOM
const obtenerPrecioPorDia = () => {
    const precioDiaElement = document.querySelector('[th\\:text="${vehicle.preuDia}"]');
    if (precioDiaElement) {
        const valor = parseFloat(precioDiaElement.textContent.trim());
        return isNaN(valor) ? 0 : valor;
    }
    return 0;
};

const obtenerPrecioFianza = () => {
    const precioFianzaElement = document.querySelector('[th\\:text="${vehicle.fianca}"]');
    if (precioFianzaElement) {
        const valor = parseFloat(precioFianzaElement.textContent.trim());
        return isNaN(valor) ? 0 : valor;
    }
    return 0;
};


// Función para obtener la fecha actual en formato ISO (YYYY-MM-DD)
const obtenerFechaActual = () => {
    const hoy = new Date();
    const anio = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, '0');
    const dia = String(hoy.getDate()).padStart(2, '0');
    return `${anio}-${mes}-${dia}`;
};

// Función para obtener la hora actual en formato HH:mm
const obtenerHoraActual = () => {
    const ahora = new Date();
    const horas = String(ahora.getHours()).padStart(2, '0');
    const minutos = String(ahora.getMinutes()).padStart(2, '0');
    return `${horas}:${minutos}`;
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
        return Math.ceil(diferenciaMs / (1000 * 60 * 60 * 24)); // Días inclusivos
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

    // Validar y calcular
    diasReserva.textContent = dias; // Actualizar número de días
    if (dias > 0) {
        const total = precioPorDia * dias * precioFianza;
        precioTotal.textContent = `${total.toFixed(2)} €`; // Actualizar total en el DOM
        console.log('Precio total actualizado:', total); // Verificar en consola
    } else {
        precioTotal.textContent = '0.00 €'; // Asegurar un valor consistente
    }
};

// Función para limitar las horas disponibles en "Hora recogida"
const limitarHoraRecogida = () => {
    const horaActual = obtenerHoraActual();
    const hoy = obtenerFechaActual();

    if (fechaRecogida.value === hoy) {
        // Si la fecha de recogida es hoy, deshabilita horas anteriores a la actual
        for (const opcion of horaRecogida.options) {
            opcion.disabled = opcion.value && opcion.value < horaActual;
        }
    } else {
        // Si la fecha de recogida no es hoy, habilita todas las opciones
        for (const opcion of horaRecogida.options) {
            opcion.disabled = false;
        }
    }
};

// Validar y ajustar fechas
const validarFechas = () => {
    if (fechaRecogida.value >= fechaEntrega.value) {
        const nuevaFechaEntrega = new Date(fechaRecogida.value);
        nuevaFechaEntrega.setDate(nuevaFechaEntrega.getDate() + 1);
        fechaEntrega.value = nuevaFechaEntrega.toISOString().split('T')[0];
    }
    actualizarPrecioYDias();
};

// Escuchar eventos
fechaRecogida.addEventListener('input', () => {
    const nuevaFechaEntregaMin = new Date(fechaRecogida.value);
    nuevaFechaEntregaMin.setDate(nuevaFechaEntregaMin.getDate() + 1);
    fechaEntrega.min = nuevaFechaEntregaMin.toISOString().split('T')[0];

    limitarHoraRecogida(); // Limita las horas si es necesario
    validarFechas(); // Validar fechas
});

fechaEntrega.addEventListener('input', validarFechas);
horaRecogida.addEventListener('input', limitarHoraRecogida);

// Inicialización al cargar la página
window.addEventListener('DOMContentLoaded', () => {
    establecerFechasIniciales();
    limitarHoraRecogida();
    actualizarPrecioYDias();
});
