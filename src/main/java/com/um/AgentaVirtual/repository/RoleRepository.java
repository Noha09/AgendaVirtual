package com.um.AgentaVirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.um.AgentaVirtual.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
