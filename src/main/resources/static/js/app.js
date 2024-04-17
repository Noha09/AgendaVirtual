function eliminarTarea(element) {
	let ok = confirm('Estas Seguro de Eliminar Esta Tarea?');

	if (ok) {
		element.nextElementSibling.submit();
	}
}

let elementosCarta = document.querySelectorAll('.card');

let fechaActual = new Date();

// console.log("Limite: " + fechaActual.toLocaleDateString('es-ES'));

elementosCarta.forEach((element) => {
	let pivote = element.querySelector('.pivote').textContent;
	let estado = element.querySelector('.estado');
	let fechaThymeleaf = element.querySelector('.fechalim').textContent;
	
	let partesFecha = fechaThymeleaf.split('/');

	let fechaThymeleafObj = new Date(partesFecha[2], partesFecha[1] - 1, partesFecha[0]);

	//let checkbox = document.getElementById('entregado');

	// console.log("Fecha: " + fechaThymeleafObj.toLocaleDateString('es-ES'));

	if (pivote === "true") {
		estado.classList.add('btn-success');
		estado.textContent = 'Entregado';
	} else {
		if (fechaThymeleafObj > fechaActual) {
			estado.classList.add('btn-warning');
			estado.textContent = 'Pendiente';
		} else if (fechaThymeleafObj < fechaActual) {
			estado.classList.add('btn-danger');
			estado.textContent = 'Atrasado';
		}
	}
});