package com.um.AgentaVirtual.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.um.AgentaVirtual.model.Contacto;
import com.um.AgentaVirtual.model.Usuario;
import com.um.AgentaVirtual.repository.ContactoRepository;
import com.um.AgentaVirtual.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("contacto")
public class ContactoController {
	@Autowired
	private ContactoRepository contactoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/admin")
	String index(Pageable pageable, Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Usuario u = usuarioRepository.findByEmail(auth.getName());
		
		List<Contacto> contactos = contactoRepository.findByIdUsuario(u.getId());
		
		model.addAttribute("contactos", contactos);
		
		return "indexContacto";
	}
	
	@GetMapping("/nuevo")
	String nuevo(Model model) {
		model.addAttribute("contacto", new Contacto());
		return "formularioContacto";
	}
	
	@PostMapping("/nuevo")
	String crear(
			@Validated Contacto contacto, 
			BindingResult br , 
			RedirectAttributes ra, 
			Model model, 
			HttpServletRequest request
	) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (br.hasErrors()) {
			model.addAttribute("contacto", contacto);
			return "formularioContacto";
		}
		
		Usuario u = usuarioRepository.findByEmail(auth.getName());
		
		contacto.setIdUsuario(u.getId());
		
		contactoRepository.save(contacto);
		
		ra.addFlashAttribute("msgExito", "El contacto se a creado correctamente");
		
		return "redirect:/contacto/admin";
	}
	
	@GetMapping("/{id}/editar")
	String editar(@PathVariable Integer id, Model model) {
		Contacto contacto = contactoRepository.getById(id);
		
		model.addAttribute("contacto", contacto);
		return "formularioContacto";
	}
	
	@PostMapping("/{id}/editar")
	String actualizar(	
			@PathVariable Integer id, 
			@Validated Contacto contacto, 
			BindingResult br, 
			RedirectAttributes ra, 
			Model model
	) {
		if (br.hasErrors()) {
			model.addAttribute("contacto", contacto);
			return "formularioContacto";
		}
		
		Contacto contactoFromDB = contactoRepository.getById(id);
		
		contactoFromDB.setNombre(contacto.getNombre());
		contactoFromDB.setFechaNacimiento(contacto.getFechaNacimiento());
		contactoFromDB.setCelular(contacto.getCelular());
		contactoFromDB.setEmail(contacto.getEmail());
		
		contactoRepository.save(contactoFromDB);
		
		ra.addFlashAttribute("msgExito", "El contacto se a actualizaco correctamente");
		
		return "redirect:/contacto/admin";
	}
	
	@PostMapping("/{id}/eliminar")
	String eliminar(@PathVariable Integer id, RedirectAttributes ra) {
		Contacto contacto = contactoRepository.getById(id);
		
		contactoRepository.delete(contacto);
		
		ra.addFlashAttribute("msgExito", "El contacto se a eliminado correctamente");
		
		return "redirect:/contacto/admin";
	}
}
