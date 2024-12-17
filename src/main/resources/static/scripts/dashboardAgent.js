
// Seleccionar el contenedor de 'marxes' y los radios de 'caixesCanvis'
const vehiclesSection = document.getElementById("cars");
const reservesSection = document.getElementById("res");
const incidenciesSection = document.getElementById("inc");

const vehicleButton = document.getElementById("car-btn");
const reservesButton = document.getElementById("res-btn");
const incidenciesButton = document.getElementById("inc-btn");

function mostrarSection(section, button) {
    vehiclesSection.classList.remove("active");
    reservesSection.classList.remove("active");
    incidenciesSection.classList.remove("active");

    vehicleButton.classList.remove("active");
    reservesButton.classList.remove("active");
    incidenciesButton.classList.remove("active");

    section.classList.add("active");
    button.classList.add("active")

}

vehicleButton.addEventListener("click", () => {
    mostrarSection(vehiclesSection, vehicleButton);
});


reservesButton.addEventListener("click", () => {
    mostrarSection(reservesSection, reservesButton);
});


incidenciesButton.addEventListener("click", () => {
    mostrarSection(incidenciesSection, incidenciesButton);
});

mostrarSection(vehiclesSection, vehicleButton);

const formFiltres = document.getElementById("filtres-form");
const buttonFiltres = document.getElementById("applyFilters");

buttonFiltres.addEventListener("click", () => {
    formFiltres.submit();
})