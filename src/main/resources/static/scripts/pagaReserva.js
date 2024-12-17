// Cargar el total desde localStorage y mostrarlo en la página
window.addEventListener('DOMContentLoaded', () => {
    // Recuperar el total de la reserva almacenado en localStorage
    const totalReserva = localStorage.getItem('reserva-precio-total'); // Consistencia con la clave del localStorage

    if (totalReserva) {
        // Convertir el valor de localStorage a número
        const totalNumerico = parseFloat(totalReserva);

        // Validar que el valor convertido sea un número
        if (!isNaN(totalNumerico)) {
            // Seleccionar el elemento donde se muestra el total
            const totalElement = document.querySelector('.cost-summary strong');
            if (totalElement) {
                totalElement.textContent = `${totalNumerico.toFixed(2)} €`; // Mostrar el total formateado
            } else {
                console.error('No se encontró el elemento .cost-summary strong en el DOM.');
            }
        } else {
            console.error('El valor almacenado no es un número válido:', totalReserva);
        }
    } else {
        console.error('No se encontró el total de la reserva en localStorage.');
    }

    // Agregar validaciones al formulario de pago
    const form = document.querySelector('.payment-section form');
    if (form) {
        form.addEventListener('submit', (event) => {
            if (!validarFormulario()) {
                event.preventDefault(); // Prevenir el envío del formulario si no es válido
            }
        });
    } else {
        console.error('No se encontró el formulario en la sección de pago.');
    }
});

// Función para validar los campos del formulario de pago
function validarFormulario() {
    const nombreTarjeta = document.getElementById('nombre-tarjeta')?.value.trim();
    const numeroTarjeta = document.getElementById('numero-tarjeta')?.value.trim();
    const fechaCaducidad = document.getElementById('fecha-caducidad')?.value.trim();
    const cvv = document.getElementById('cvv')?.value.trim();

    if (!nombreTarjeta || !numeroTarjeta || !fechaCaducidad || !cvv) {
        alert('Por favor, complete todos los campos.');
        console.error('Campos vacíos detectados en el formulario de pago.');
        return false;
    }

    if (!/^\d{16}$/.test(numeroTarjeta.replace(/\s+/g, ''))) {
        alert('El número de tarjeta debe tener 16 dígitos.');
        console.error('Número de tarjeta inválido:', numeroTarjeta);
        return false;
    }

    if (!/^\d{3}$/.test(cvv)) {
        alert('El CVV debe tener 3 dígitos.');
        console.error('CVV inválido:', cvv);
        return false;
    }

    if (!/^\d{2}\/\d{4}$/.test(fechaCaducidad)) {
        alert('La fecha de caducidad debe tener el formato MM/AAAA.');
        console.error('Fecha de caducidad inválida:', fechaCaducidad);
        return false;
    }

    return true; // El formulario es válido
}