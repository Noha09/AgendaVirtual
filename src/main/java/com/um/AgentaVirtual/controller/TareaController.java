package com.um.AgentaVirtual.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.um.AgentaVirtual.model.Tarea;
import com.um.AgentaVirtual.model.Usuario;
import com.um.AgentaVirtual.repository.TareaRepository;
import com.um.AgentaVirtual.repository.UsuarioRepository;

@Controller
@RequestMapping("tarea")
public class TareaController {
	@Autowired
	private TareaRepository tareaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/admin")
	String index(
			Pageable pageable, 
			Model model, 
			HttpServletRequest request
	) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Usuario u = usuarioRepository.findByEmail(auth.getName());
		
		List<Tarea> tareas = tareaRepository.findByIdUsuario(u.getId()); // Buscar las tareas de cada usuario
		List<Tarea> tareasOrdenadas = tareas.stream()
				.sorted(Comparator.comparing(Tarea::getFechaLimite)) // Ordenar por fechas de forma ascendente
				.collect(Collectors.toList());
		
		model.addAttribute("tareas", tareasOrdenadas);
		
		return "indexTarea";
	}
	
	@GetMapping("/pendientes")
	String pendientes(
			Pageable pageable, 
			Model model, 
			HttpServletRequest request
	) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Usuario u = usuarioRepository.findByEmail(auth.getName());
		
		List<Tarea> tareasPendientes = tareaRepository.findByIdUsuarioAndStatus(u.getId(), false);
		
		Date hoy = new Date();
		List<Tarea> tareasFiltradas = new ArrayList<>();
		
		for (Tarea tarea : tareasPendientes) {
			int comparacion = tarea.getFechaLimite().toInstant().compareTo(hoy.toInstant());
			
			if (comparacion > 0) {
				tareasFiltradas.add(tarea);
			}
		}
		
		List<Tarea> tareasOrdenadas = tareasFiltradas.stream()
				.sorted(Comparator.comparing(Tarea::getFechaLimite))
				.collect(Collectors.toList());
		
		model.addAttribute("tareas", tareasOrdenadas);
		
		return "indexTarea";
	}
	
	@GetMapping("/atrasadas")
	String atrasadas(
			Pageable pageable, 
			Model model, 
			HttpServletRequest request
	) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Usuario u = usuarioRepository.findByEmail(auth.getName());
		
		List<Tarea> tareasAtrasadas = tareaRepository.findByIdUsuarioAndStatus(u.getId(), false);
		
		Date hoy = new Date();
		List<Tarea> tareasFiltradas = new ArrayList<>();
		
		for (Tarea tarea : tareasAtrasadas) {
			int comparacion = tarea.getFechaLimite().toInstant().compareTo(hoy.toInstant());
			
			if (comparacion < 0) {
				tareasFiltradas.add(tarea);
			}
		}
		
		List<Tarea> tareasOrdenadas = tareasFiltradas.stream()
				.sorted(Comparator.comparing(Tarea::getFechaLimite))
				.collect(Collectors.toList());
		
		model.addAttribute("tareas", tareasOrdenadas);
		
		return "indexTarea";
	}
	
	@GetMapping("/completadas")
	String completadas(
			Pageable pageable, 
			Model model, 
			HttpServletRequest request
	) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Usuario u = usuarioRepository.findByEmail(auth.getName());
		
		List<Tarea> tareasCompletadas = tareaRepository.findByIdUsuarioAndStatus(u.getId(), true);
		List<Tarea> tareasOrdenadas = tareasCompletadas.stream()
				.sorted(Comparator.comparing(Tarea::getFechaLimite))
				.collect(Collectors.toList());
		
		model.addAttribute("tareas", tareasOrdenadas);
		
		return "indexTarea";
	}
	
	@GetMapping("/nuevo")
	String nuevo(Model model) {
		model.addAttribute("tarea", new Tarea());
		return "formularioTarea";
	}
	
	@PostMapping("/nuevo")
	String nuevo(
			@Validated Tarea tarea, 
			BindingResult br , 
			RedirectAttributes ra, 
			Model model, 
			HttpServletRequest request
	) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (br.hasErrors()) {
			model.addAttribute("tarea", tarea);
			return "formularioTarea";
		}
		
		Usuario u = usuarioRepository.findByEmail(auth.getName());
		
		LocalDate localDate = LocalDate.now();
		Date date = java.sql.Date.valueOf(localDate);
		
		tarea.setFechaRegistro(date);
		tarea.setIdUsuario(u.getId());
		tarea.setStatus(false);
		
		tareaRepository.save(tarea);
		
		ra.addFlashAttribute("msgExito", "La tarea se a creado correctamente");
		
		return "redirect:/tarea/admin";
	}
	
	@GetMapping("/{id}/editar")
	String editar(
			@PathVariable Integer id, 
			Model model
	) {
		Tarea tarea = tareaRepository.getById(id);
		
		model.addAttribute("tarea", tarea);
		return "formularioTarea";
	}
	
	@PostMapping("/{id}/editar")
	String actualizar(
			@PathVariable Integer id, 
			@Validated Tarea tarea,
			@RequestParam("status") boolean status,
			BindingResult br, 
			RedirectAttributes ra, 
			Model model
	) {
		if (br.hasErrors()) {
			model.addAttribute("tarea", tarea);
			return "formularioTarea";
		}
		
		Tarea tareaFromDB = tareaRepository.getById(id);
		
		tareaFromDB.setTitulo(tarea.getTitulo());
		tareaFromDB.setFechaLimite(tarea.getFechaLimite());
		tareaFromDB.setDescripcion(tarea.getDescripcion());
		tareaFromDB.setStatus(status);
		
		tareaRepository.save(tareaFromDB);
		
		ra.addFlashAttribute("msgExito", "La tarea se a actualizaco correctamente");
		
		return "redirect:/tarea/admin";
	}
	
	@PostMapping("/{id}/eliminar")
	String eliminar(
			@PathVariable Integer id, 
			RedirectAttributes ra
	) {
		Tarea tarea = tareaRepository.getById(id);
		
		tareaRepository.delete(tarea);
		
		ra.addFlashAttribute("msgExito", "La tarea se a eliminado correctamente");
		
		return "redirect:/tarea/admin";
	}
}
