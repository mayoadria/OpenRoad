let valoracioAct = 0;

function selEstrella(valor) {
    if (valor === valoracioAct) {
        valoracioAct = 0;
        desEstrelles();
    } else {
        valoracioAct = valor;
        actEstrelles(valor);
    }
    document.getElementById("valoracio").value = valoracioAct; // Actualiza el input oculto
}

function actEstrelles(valor) {
    const estrellas = document.querySelectorAll(".fa-star");
    estrellas.forEach((estrella, index) => {
        if (index < valor) {
            estrella.classList.add("checked");
        } else {
            estrella.classList.remove("checked");
        }
    });
}

function desEstrelles() {
    const estrellas = document.querySelectorAll(".fa-star");
    estrellas.forEach(estrella => estrella.classList.remove("checked"));
}