function eliminarTarea(element) {
	const swalWithBootstrapButtons = Swal.mixin({
		customClass: {
		  confirmButton: "btn btn-success",
		  cancelButton: "btn btn-danger"
		},
		buttonsStyling: false
  	});

  	swalWithBootstrapButtons.fire({
		title: "Estas seguro de eliminar esta Tarea?",
		text: "You won't be able to revert this!",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Si, borrar!",
		cancelButtonText: "No, cancelar!",
		reverseButtons: true
  	}).then((result) => {
		if (result.isConfirmed) {
			element.nextElementSibling.submit();

		  	swalWithBootstrapButtons.fire({
				title: "Eliminado!",
				text: "Tarea eliminado correctamente.",
				icon: "success"
		  	});
		} else if (
		  	/* Read more about handling dismissals below */
		  	result.dismiss === Swal.DismissReason.cancel
		) {
		  	swalWithBootstrapButtons.fire({
				title: "Cancelado",
				text: "Your imaginary file is safe :)",
				icon: "error"
		  	});
		}
	});
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