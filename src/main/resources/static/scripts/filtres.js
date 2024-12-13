// Obtener el botón y el menú lateral
const openSidebarButton = document.getElementById('openSidebar');
const sidebar = document.getElementById('sidebar');
const filtres = document.getElementById('filtres')

// Obtener el input tipo range y el span donde se muestra el valor
const minRange = document.getElementById('min-dies');
const maxRange = document.getElementById('max-dies');
const minValue = document.getElementById('min-value');
const maxValue = document.getElementById('max-value');

// Sincronizar los valores de los rangos
minRange.addEventListener('input', function() {
    if (parseInt(minRange.value) > parseInt(maxRange.value)) {
        minRange.value = maxRange.value;
    }
    minValue.textContent = minRange.value;
});

maxRange.addEventListener('input', function() {
    if (parseInt(maxRange.value) < parseInt(minRange.value)) {
        maxRange.value = minRange.value;
    }
    maxValue.textContent = maxRange.value;
}); 


// Añadir evento para abrir el menú lateral cuando se haga clic en el botón
openSidebarButton.addEventListener('click', function () {
    sidebar.classList.toggle('open'); // Togglear la clase 'open'
    filtres.classList.toggle('open');
    
  });

// Aplicar filtros automáticamente
document.getElementById('applyFilters').addEventListener('click', function () {
    const price = document.getElementById('price').value;
    const fuel = document.getElementById('fuel').value;
    const transmission = document.getElementById('transmission').value;

    // Realiza una solicitud al servidor para actualizar los vehículos filtrados
    fetch(`/api/vehicles?price=${price}&fuel=${fuel}&transmission=${transmission}`)
        .then((response) => response.json())
        .then((vehicles) => {
            // Llama a una función para renderizar la lista de vehículos
            updateVehicleList(vehicles);
        });
});

// Función para renderizar los vehículos
function updateVehicleList(vehicles) {
    const container = document.getElementById('vehicle-list');
    container.innerHTML = ''; // Limpia los vehículos existentes
    vehicles.forEach((vehicle) => {
        const vehicleCard = document.createElement('div');
        vehicleCard.className = 'vehicle-card';
        vehicleCard.innerHTML = `
        <h3>${vehicle.name}</h3>
        <p>Precio: ${vehicle.price} €</p>
        <p>Combustible: ${vehicle.fuel}</p>
        <p>Transmisión: ${vehicle.transmission}</p>
      `;
        container.appendChild(vehicleCard);
    });
}
