// Elementos del DOM
const fechaRecogida = document.getElementById('fechaRecogida');
const horaRecogida = document.getElementById('horaRecogida');
const fechaEntrega = document.getElementById('fechaEntrega');
const diasReserva = document.getElementById('diasReserva');
const precioTotal = document.getElementById('precioTotal');

// Obtener precios dinámicamente desde el DOM
const obtenerPrecioPorDia = () => {
    const precioDiaElement = document.getElementById('preuDia');
    if (precioDiaElement) {
        // Extraer solo el valor numérico (removiendo el símbolo '€')
        const valor = parseFloat(precioDiaElement.textContent.replace('€', '').trim());
        return isNaN(valor) ? 0 : valor;
    }
    return 0;
};

const obtenerPrecioFianza = () => {
    const precioFianzaElement = document.getElementById('precioFianza');
    if (precioFianzaElement) {
        // Extraer solo el valor numérico (removiendo el símbolo '€')
        const valor = parseFloat(precioFianzaElement.textContent.replace('€', '').trim());
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

    // Validar fechas
    if (isNaN(fechaInicio.getTime()) || isNaN(fechaFin.getTime())) {
        return 0; // Devuelve 0 si las fechas son inválidas
    }

    const diferenciaMilisegundos = fechaFin.getTime() - fechaInicio.getTime();
    const dias = diferenciaMilisegundos / (1000 * 60 * 60 * 24); // Convertir a días
    return Math.ceil(dias); // Redondear hacia arriba si hay horas parciales
};

// Función para actualizar el precio total y los días de reserva
const actualizarPrecioYDias = () => {
    const precioPorDia = obtenerPrecioPorDia();
    const precioFianza = obtenerPrecioFianza();

    const inicio = fechaRecogida.value;
    const fin = fechaEntrega.value;

    const dias = calcularDias(inicio, fin);

    // Actualizar número de días (convertir a string)
    diasReserva.textContent = `${dias}`;

    // Calcular el total
    const precioFianzaNumerico = Number(precioFianza); // Asegurar tipo numérico
    const total = (precioPorDia * dias) + precioFianzaNumerico;

    // Actualizar el precio total en el DOM
    precioTotal.textContent = `${total.toFixed(2)} €`;
};

// Función para inicializar el precio al cargar la página
const inicializarPrecio = () => {
    actualizarPrecioYDias(); // Ejecuta el cálculo inicial
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

// Ejecutar el cálculo inicial al cargar la página
window.addEventListener('DOMContentLoaded', () => {
    establecerFechasIniciales();
    limitarHoraRecogida();
    inicializarPrecio(); // Calcular y actualizar precio al cargar
});
