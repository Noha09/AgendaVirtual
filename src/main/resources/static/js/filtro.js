// Función para normalizar una fecha a UTC
function normalizarFechaUTC(fecha) {
    let fechaObj = new Date(fecha);
    let fechaUTC = new Date(Date.UTC(
        fechaObj.getUTCFullYear(),
        fechaObj.getUTCMonth(),
        fechaObj.getUTCDate() + 1,
        fechaObj.getUTCHours() - 18,
        fechaObj.getUTCMinutes(),
        fechaObj.getUTCSeconds()
    ));
    return fechaUTC;
}

// Función para filtrar las tareas por fecha
function filtrarTareasPorFecha() {
	let fechaSeleccionada = document.getElementById("fechaFiltro").value;
	let fechaFormateada = normalizarFechaUTC(fechaSeleccionada);
    let listaTareas = document.querySelectorAll(".card");
    
    listaTareas.forEach(function (tarea) {
		let fechaTarea = tarea.querySelector(".fechalim").textContent;
		let partesFecha = fechaTarea.split('/');
		let fechaThymeleafObj = new Date(partesFecha[2], partesFecha[1] - 1, partesFecha[0]);
		
		// Mostrar u ocultar la tarea según la fecha seleccionada
        if (fechaThymeleafObj.getTime() !== fechaFormateada.getTime()) {
            tarea.style.display = "none";
        }
	});
	
	let tareas = listaTareas.children;
	console.log(tareas);
	let tareasFiltradas = [];
        
    for (let i = 0; i < tareas.length; i++) {
		if (tareas[i].style.display !== "none") {
            tareasFiltradas.push(tareas[i]);
        }
	}
	
	for (var i = 0; i < tareasFiltradas.length; i++) {
        listaTareas.insertBefore(tareasFiltradas[i], listaTareas.firstChild);
    }
}

// Escuchar el evento de cambio en el campo de fecha
document.getElementById("fechaFiltro").addEventListener("change", filtrarTareasPorFecha);