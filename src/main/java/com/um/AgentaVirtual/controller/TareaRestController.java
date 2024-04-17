package com.um.AgentaVirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.AgentaVirtual.model.Tarea;
import com.um.AgentaVirtual.model.Usuario;
import com.um.AgentaVirtual.repository.TareaRepository;
import com.um.AgentaVirtual.repository.UsuarioRepository;

@RestController
@RequestMapping("api")
public class TareaRestController {
	
	@Autowired
	private TareaRepository tareaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("find")
	public String find() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Usuario u = usuarioRepository.findByEmail(auth.getName());
		
		List<Tarea> tareas = tareaRepository.findByIdUsuario(u.getId());
		ObjectMapper objectMapper = new ObjectMapper(); // Inicializar un objeto ObjectMapper, que se utiliza para convertir objetos Java a JSON y viceversa
		String json = null;
		
		try {
            json = objectMapper.writeValueAsString(tareas); // Convertir la lista de tareas a una cadena JSON
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
		
		return json;
	}
}
