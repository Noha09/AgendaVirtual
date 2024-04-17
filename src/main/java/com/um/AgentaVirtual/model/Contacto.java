package com.um.AgentaVirtual.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "contacto")
public class Contacto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcontacto")
	private Integer id;
	
	@NotBlank(message = "Este Campo Es Obligatorio")
	private String nombre;
	
	@PastOrPresent(message = "El Valor No Debe Estar En Futuro")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "fechanac")
	private LocalDate fechaNacimiento;
	
	@Size(max = 10, message = "El Valor Debe Tener 10 Caracteres Como Maximo")
	private String celular;
	
	@Email(message = "Ingrese Un Correo Electronico Valido")
	private String email;
	
	@Column(name = "idusuario")
    private Integer idUsuario; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
}
