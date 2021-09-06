package com.scrum.ude.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.scrum.ude.dao.ITareaDAO;
import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.entity.Tarea;
import com.scrum.ude.service.ProyectoServiceImpl;
import com.scrum.ude.service.TareaServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TareaController {

	@Autowired
	private ProyectoServiceImpl proyectoServiceImp;

	@Autowired
	private TareaServiceImpl tareaServiceImp;

	@Autowired
	private ITareaDAO tareaDAO;

	@PostMapping("/crearTarea")
	public String crearTarea(Model model, @RequestParam(value = "pass", defaultValue = "0") Long idProyecto, Tarea tarea) {

		Proyecto proyecto = proyectoServiceImp.buscarPorIdProyecto(idProyecto);
		tarea.setProyecto(proyecto);
		Tarea tareaBase = tareaServiceImp.buscarPorNombreTarea(tarea.getNombre());

		if (tareaBase == null) {
			tareaDAO.save(tarea);
		}

		return "redirect:/verProyectoTarea/" + idProyecto + "";
	}

	@PostMapping("/actualizar-tarea")
	public String update(@ModelAttribute Tarea tarea) {
		// actualizar usuario
		Tarea tareaBase =tareaServiceImp.buscarPorIdTarea(tarea.getId());
		tareaBase.setNombre(tarea.getNombre());
		tareaBase.setDescripcion(tarea.getDescripcion());
		
		tareaDAO.save(tareaBase);
		
		return "redirect:/verProyectoTarea/" + tareaBase.getProyecto().getId() + "";
	}

	@GetMapping(value = "/eliminarTarea/{id}")
	public String eliminarTarea(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Long idProyecto = null;
		if (id > 0 && id != null) {
			Tarea tarea = tareaServiceImp.buscarPorIdTarea(id);
			idProyecto = tarea.getProyecto().getId();
			tareaDAO.deleteById(id);
			flash.addFlashAttribute("success", "Tarea  eliminada con éxito!");
		}

		return "redirect:/verProyectoTarea/" + idProyecto + "";
	}

}
