package com.scrum.ude.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.scrum.ude.dao.ITareaDAO;
import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.entity.Tarea;
import com.scrum.ude.service.ProyectoServiceImpl;
import com.scrum.ude.service.TareaServiceImpl;

@Controller
public class TareaController {
	
	@Autowired
	private ProyectoServiceImpl proyectoServiceImp;
	
	@Autowired
	private TareaServiceImpl tareaServiceImp;


	@Autowired
	private ITareaDAO tareaDAO;

	// navego hacia el formulario de la creacion de la tarea con el id del proyecto
	
	@GetMapping("/crearTarea/{id}")
	public String verTarea(Model model,@PathVariable(value = "id") Long id ) {

		Tarea tarea= new Tarea();
		model.addAttribute("ide",id);
		model.addAttribute("tarea",tarea);

		return"/proyecto/crearTarea";
	}
	@PostMapping("/crearTarea")
	public String crearTarea(Model model,@RequestParam(value="pass",defaultValue="0")Long idProyecto, Tarea tarea) {

		Proyecto proyecto=proyectoServiceImp.buscarPorIdProyecto(idProyecto);
		tarea.setProyecto(proyecto);
		Tarea tareaBase=tareaServiceImp.buscarPorNombreTarea(tarea.getNombre());
		
		if(tareaBase==null) {
			tareaDAO.save(tarea);
		} else {
			// ya existe dicha tarea 
			
		 }
		
    	 return "redirect:/verProyectoTarea/"+idProyecto+"";
	}
	
	// aca elimino una tarea
			@GetMapping(value = "/eliminarTarea/{id}")
			public String eliminarTarea(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
				Long idProyecto=null;
				if (id > 0) {
				Tarea tarea= tareaServiceImp.buscarPorIdTarea(id);
				idProyecto=tarea.getProyecto().getId();
					tareaDAO.deleteById(id);
					flash.addFlashAttribute("success", "Tarea  eliminada con éxito!");
				}
				
				 return "redirect:/verProyectoTarea/"+idProyecto+"";
			}
	
	
}
