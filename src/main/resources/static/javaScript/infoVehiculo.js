// Precio por día
const precioPorDia = 75;

// Elementos del DOM
const fechaRecogida = document.getElementById('fechaRecogida');
const horaRecogida = document.getElementById('horaRecogida');
const fechaEntrega = document.getElementById('fechaEntrega');
const diasReserva = document.getElementById('diasReserva');
const precioTotal = document.getElementById('precioTotal');

// Función para obtener la fecha y hora actual formateadas
const obtenerFechaHoraActual = () => {
    const ahora = new Date();
    const anio = ahora.getFullYear();
    const mes = String(ahora.getMonth() + 1).padStart(2, '0');
    const dia = String(ahora.getDate()).padStart(2, '0');
    const horas = String(ahora.getHours()).padStart(2, '0');
    const minutos = String(ahora.getMinutes()).padStart(2, '0');
    return {
        fecha: `${anio}-${mes}-${dia}`,
        hora: `${horas}:${minutos}`
    };
};

// Establecer valores predeterminados
const { fecha, hora } = obtenerFechaHoraActual();
fechaRecogida.value = fecha;
fechaEntrega.value = fecha;
fechaEntrega.min = fecha; // Fecha mínima para entrega es hoy
horaRecogida.value = hora;

// Validar y calcular los días y el precio
const calcularPrecio = () => {
    const inicio = new Date(fechaRecogida.value);
    const fin = new Date(fechaEntrega.value);

    if (!isNaN(inicio) && !isNaN(fin) && fin >= inicio) {
        // Calcular la diferencia en días
        const diferenciaDias = Math.ceil((fin - inicio) / (1000 * 60 * 60 * 24));
        diasReserva.textContent = diferenciaDias;
        precioTotal.textContent = `${diferenciaDias * precioPorDia} €`;
    } else {
        // Restablecer si las fechas no son válidas
        diasReserva.textContent = '0';
        precioTotal.textContent = '0 €';
    }
};

// Prevenir fechas inválidas en "Entrega"
fechaRecogida.addEventListener('input', () => {
    fechaEntrega.min = fechaRecogida.value; // La fecha de entrega no puede ser anterior a la recogida
    calcularPrecio(); // Recalcular si se cambia la fecha de recogida
});

fechaEntrega.addEventListener('input', calcularPrecio);

// Validar horas de recogida (solo para hoy)
horaRecogida.addEventListener('input', () => {
    const hoy = new Date(fechaRecogida.value).toDateString() === new Date().toDateString();
    const horaActual = obtenerFechaHoraActual().hora;
    if (hoy && horaRecogida.value < horaActual) {
        horaRecogida.value = horaActual; // Ajustar a la hora actual si es inválida
    }
});
