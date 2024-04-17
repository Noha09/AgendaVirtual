package com.um.AgentaVirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.um.AgentaVirtual.model.Role_usuario;

@Repository
public interface Role_usuarioRepository extends JpaRepository<Role_usuario, Integer>{

}
