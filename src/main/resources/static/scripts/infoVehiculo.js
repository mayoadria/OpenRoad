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
        const valor = parseFloat(precioDiaElement.textContent.replace('€', '').trim());
        return isNaN(valor) ? 0 : valor;
    }
    return 0;
};

const obtenerPrecioFianza = () => {
    const precioFianzaElement = document.getElementById('precioFianza');
    if (precioFianzaElement) {
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

// Función para inicializar las fechas predeterminadas (sin usar localStorage)
const establecerFechasIniciales = () => {
    const fechaActual = obtenerFechaActual();

    const manana = new Date();
    manana.setDate(manana.getDate() + 1);
    const fechaMananaISO = manana.toISOString().split('T')[0];

    fechaRecogida.value = fechaActual; // Siempre fecha actual
    fechaRecogida.min = fechaActual;

    fechaEntrega.value = fechaMananaISO; // Día siguiente
    fechaEntrega.min = fechaRecogida.value;

    actualizarPrecioYDias(); // Recalcula el precio al establecer fechas
};

// Función para calcular la diferencia en días (inclusivos)
const calcularDias = (inicio, fin) => {
    const fechaInicio = new Date(inicio);
    const fechaFin = new Date(fin);

    if (isNaN(fechaInicio.getTime()) || isNaN(fechaFin.getTime())) {
        return 0;
    }

    const diferenciaMilisegundos = fechaFin.getTime() - fechaInicio.getTime();
    const dias = diferenciaMilisegundos / (1000 * 60 * 60 * 24);
    return Math.ceil(dias);
};

// Función para actualizar el precio total y los días de reserva
const actualizarPrecioYDias = () => {
    const precioPorDia = obtenerPrecioPorDia();
    const precioFianza = obtenerPrecioFianza();

    const inicio = fechaRecogida.value;
    const fin = fechaEntrega.value;

    const dias = calcularDias(inicio, fin);

    diasReserva.textContent = `${dias}`;

    const precioFianzaNumerico = Number(precioFianza);
    const total = (precioPorDia * dias) + precioFianzaNumerico;

    precioTotal.textContent = `${total.toFixed(2)} €`;

    // Guardar datos en localStorage
    localStorage.setItem('reserva-precio-total', total.toFixed(2));

    // Asignar el precio total al campo oculto del formulario
    const inputPrecioTotal = document.getElementById("preuComplert");
    if (inputPrecioTotal) {
        inputPrecioTotal.value = total.toFixed(2);
    }
};

// Función para inicializar el precio al cargar la página
const inicializarPrecio = () => {
    actualizarPrecioYDias();
};


// Función para limitar las horas disponibles en "Hora recogida"
const limitarHoraRecogida = () => {
    const horaActual = obtenerHoraActual();
    const hoy = obtenerFechaActual();

    if (fechaRecogida.value === hoy) {
        let horaInvalida = true;

        for (const opcion of horaRecogida.options) {
            if (opcion.value < horaActual) {
                opcion.disabled = true;
            } else {
                opcion.disabled = false;
                if (horaInvalida) {
                    horaRecogida.value = opcion.value;
                    horaInvalida = false;
                }
            }
        }
    } else {
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

    limitarHoraRecogida();
    validarFechas();
});

fechaEntrega.addEventListener('input', () => {
    validarFechas();
    actualizarPrecioYDias(); // Asegura que se actualice el precio total.
});


horaRecogida.addEventListener('input', limitarHoraRecogida);

// Ejecutar el cálculo inicial al cargar la página
window.addEventListener('pageshow', () => {
    establecerFechasIniciales();
    limitarHoraRecogida();
    inicializarPrecio();
});


document.addEventListener("DOMContentLoaded", function () {
    const submitButton = document.getElementById("alquilarBoton");
    if (submitButton) {
        submitButton.addEventListener("click", function () {
            // Actualizar el campo oculto antes de enviar el formulario
            actualizarPrecioYDias();

            const form = document.getElementById("formReserva");
            if (form) {
                form.submit();
            }
        });
    }
});