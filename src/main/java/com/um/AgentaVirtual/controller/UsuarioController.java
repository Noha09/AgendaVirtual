package com.um.AgentaVirtual.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.um.AgentaVirtual.model.Role;
import com.um.AgentaVirtual.model.Usuario;
import com.um.AgentaVirtual.repository.RoleRepository;
import com.um.AgentaVirtual.repository.UsuarioRepository;

@Controller
public class UsuarioController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@PostMapping("/grabar")
	String grabar(HttpServletRequest request, RedirectAttributes ra, Model model) {
		int id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));
		String nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre");
		String email = request.getParameter("email") == null ? "" : request.getParameter("email");
		String password = request.getParameter("password") == null ? "" : request.getParameter("password");
		
		Usuario u = new Usuario();

		if(request.getParameter("id") != null) {
			u = usuarioRepository.getById(id);
		}
		
		u.setNombre(nombre);
		u.setEmail(email);
		u.setPassword(password);
		
		String grabaRoles = "-";
		Integer[] roles = {1};
		
		if(roles != null) {
			for(int roleId : roles) {
				Role role = roleRepository.getById(roleId);
				String rolename = role.getNombre();
				
				grabaRoles += String.valueOf(rolename)+"-";
				
				u.setRoles(grabaRoles);
			}
		}
		
		if(usuarioRepository.save(u) != null) {
			ra.addFlashAttribute("msgExito", "El Usuario se a creado correctamente");
		}
		
		return "inicio";
	}
}
