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

document.addEventListener('DOMContentLoaded', () => {
    const conductorForm = document.getElementById('conductor-form');
    const pagoForm = document.getElementById('pago-form');
    const payButton = document.querySelector('.pay-button button');

    payButton.addEventListener('click', (event) => {
        event.preventDefault(); // Evita la acción por defecto del botón.

        // Verificar los formularios individualmente
        if (!conductorForm.checkValidity()) {
            conductorForm.reportValidity(); // Muestra los errores en pantalla
            return;
        }

        if (!pagoForm.checkValidity()) {
            pagoForm.reportValidity(); // Muestra los errores en pantalla
            return;
        }

        // Si ambos formularios son válidos
        // Agregar una redirección o lógica adicional
    });
});
