package com.um.AgentaVirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.um.AgentaVirtual.model.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
	List<Contacto> findByIdUsuario(Integer idusuario);
}
