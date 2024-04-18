package com.um.AgentaVirtual.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.um.AgentaVirtual.model.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer>{
	List<Tarea> findByIdUsuario(Integer idusuario);
	
	//@Query("SELECT t.id, t.titulo, t.descripcion, t.fechaRegistro, t.fechaLimite, t.idUsuario, t.status FROM Tarea t WHERE t.idUsuario = :idusuario AND t.status = :status")
	List<Tarea> findByIdUsuarioAndStatus(Integer idusuario, Boolean status);
	
	List<Tarea> findByFechaLimiteBetween(LocalDate fechaInicio, LocalDate fechaFin);
	
	List<Tarea> findByFechaLimite(Date fecha);

	Page<Tarea> getAll(Pageable pageable);
}
