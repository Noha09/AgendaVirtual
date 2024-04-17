package com.um.AgentaVirtual.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.um.AgentaVirtual.model.Usuario;
import com.um.AgentaVirtual.model.Contacto;
import com.um.AgentaVirtual.model.Role;
import com.um.AgentaVirtual.model.Role_usuario;
import com.um.AgentaVirtual.repository.ContactoRepository;
import com.um.AgentaVirtual.repository.RoleRepository;
import com.um.AgentaVirtual.repository.Role_usuarioRepository;
import com.um.AgentaVirtual.repository.UsuarioRepository;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private Role_usuarioRepository ruRepository;
	
	@Autowired
	private ContactoRepository contactoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping({"/", "/login"})
	String login(){
		return "login";
	}
	
	@GetMapping({"/logout"})
	String logout(){
		return "redirect:/";
	}
	
	@GetMapping({"/registrar"})
	String registro(Model model){
		model.addAttribute("usuario", new Usuario());
		
		return "registrar";
	}
	
	@PostMapping("/registrar")
	String registrarUsuario(@Validated Usuario usuario, BindingResult br , RedirectAttributes ra, Model model) {
		if (br.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "registrar";
		}
		
		Role_usuario ru = new Role_usuario();
		
		usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
		
		String grabaRoles = "-";
		Integer[] roles = {1};
		
		usuario.setRoles(grabaRoles);
		
		if(roles != null) {
			for(int roleId : roles) {
				Role role = roleRepository.getById(roleId);
				String rolename = role.getNombre();
				
				grabaRoles += String.valueOf(rolename)+"-";
				
				usuario.setRoles(grabaRoles);
			}
		}
		
		usuario.setActivo(true);
		
		usuarioRepository.save(usuario);
		
		ru.setIdUsuario(usuario.getId());
		ru.setIdRole(roles[0]);
		
		ruRepository.save(ru);
		
		return "redirect:/";
	}
	
	@GetMapping("/inicio")
	String inicio(Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> grantedAuthorities = auth.getAuthorities();
		
		int id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));
		
		Usuario tmp = new Usuario();
		
		Usuario usuario = usuarioRepository.findByEmail(auth.getName());
		String role 	= "";
		List<Contacto> contactos = contactoRepository.findByIdUsuario(usuario.getId());
		
		for (GrantedAuthority grantedAuthority : grantedAuthorities){
			role = role +"-"+grantedAuthority.getAuthority();
		}
		
		String mensaje = request.getParameter("Mensaje") == null ? "1" : request.getParameter("Mensaje");
		
		boolean showModal = false;
		
		if(request.getParameter("id") != null) {
			showModal = true;
			tmp = usuarioRepository.getById(id);
		}
		
		model.addAttribute("role", role);
		model.addAttribute("tmp", tmp);
		model.addAttribute("mensaje", mensaje);
		model.addAttribute("showModal", showModal);
		model.addAttribute("usuario", usuario);
		model.addAttribute("contactos", contactos);
		
		return "inicio";
	}
}
