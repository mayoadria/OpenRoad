document.addEventListener('DOMContentLoaded', () => {
    const reservaForm = document.getElementById('reserva-form');
    const fechaRecogidaInput = document.getElementById('fechaRecogida');
    const fechaEntregaInput = document.getElementById('fechaEntrega');
    const preuTotalInput = document.getElementById('preuTotal');

    // Configurar evento para el envío del formulario
    reservaForm.addEventListener('submit', (event) => {
        // Validar que las fechas sean correctas
        const fechaRecogida = new Date(fechaRecogidaInput.value);
        const fechaEntrega = new Date(fechaEntregaInput.value);

        if (fechaRecogida >= fechaEntrega) {
            event.preventDefault();
            alert('La fecha de entrega debe ser posterior a la fecha de recogida.');
            return;
        }

        // Asegurarse de que el precio total está sincronizado
        const totalElement = document.querySelector('.cost-summary strong');
        if (totalElement) {
            const totalText = totalElement.textContent.replace('€', '').trim();
            const totalNumeric = parseFloat(totalText);

            if (!isNaN(totalNumeric)) {
                preuTotalInput.value = totalNumeric.toFixed(2);
            } else {
                console.error('El precio total no es válido:', totalText);
                event.preventDefault();
                return;
            }
        }
    });
});
