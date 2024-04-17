package com.um.AgentaVirtual.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tarea")
public class Tarea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtarea")
	private Integer id;
	
	@NotBlank(message = "Este Campo Es Obligatorio")
	private String titulo;
	
	@NotBlank(message = "Este Campo Es Obligatorio")
	@Size(min = 50, message = "El Valor Debe Tener 50 Caracteres Como Minimo")
	private String descripcion;
	
	@Column(name = "fechareg")
	private Date fechaRegistro;
	
	@FutureOrPresent(message = "El Valor Debe Estar En Futuro")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "fechalim")
	private Date fechaLimite;
	
	@Column(name = "idusuario")
    private Integer idUsuario;
	
	@Column(name = "status")
	private Boolean status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
