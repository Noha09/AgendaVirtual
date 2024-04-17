package com.um.AgentaVirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.um.AgentaVirtual.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	// Método para buscar un usuario por su email
	Usuario findByEmail(String email);
}
