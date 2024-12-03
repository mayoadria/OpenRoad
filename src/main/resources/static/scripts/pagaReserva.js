// Cargar el total desde localStorage y mostrarlo en la página
window.addEventListener('DOMContentLoaded', () => {
    // Recuperar el total de la reserva almacenado en localStorage
    const totalReserva = localStorage.getItem('totalReserva');

    if (totalReserva) {
        // Seleccionar el elemento donde se muestra el total
        const totalElement = document.querySelector('.cost-summary strong');
        totalElement.textContent = totalReserva; // Mostrar el total recuperado
    } else {
        console.error('No se encontró el total de la reserva en localStorage.');
    }

    // Agregar validaciones al formulario de pago
    const form = document.querySelector('.payment-section form');
    form.addEventListener('submit', (event) => {
        if (!validarFormulario()) {
            event.preventDefault(); // Prevenir el envío del formulario si no es válido
        }
    });
});

// Función para validar los campos del formulario de pago
function validarFormulario() {
    const nombreTarjeta = document.getElementById('nombre-tarjeta').value.trim();
    const numeroTarjeta = document.getElementById('numero-tarjeta').value.trim();
    const fechaCaducidad = document.getElementById('fecha-caducidad').value.trim();
    const cvv = document.getElementById('cvv').value.trim();

    if (nombreTarjeta === '' || numeroTarjeta === '' || fechaCaducidad === '' || cvv === '') {
        alert('Por favor, complete todos los campos.');
        return false;
    }

    if (!/^\d{16}$/.test(numeroTarjeta.replace(/\s+/g, ''))) {
        alert('El número de tarjeta debe tener 16 dígitos.');
        return false;
    }

    if (!/^\d{3}$/.test(cvv)) {
        alert('El CVV debe tener 3 dígitos.');
        return false;
    }

    if (!/^\d{2}\/\d{4}$/.test(fechaCaducidad)) {
        alert('La fecha de caducidad debe tener el formato MM/AAAA.');
        return false;
    }

    return true; // El formulario es válido
}
