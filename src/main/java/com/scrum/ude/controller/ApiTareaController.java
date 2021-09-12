package com.scrum.ude.controller;

import com.scrum.ude.entity.Tarea;
import com.scrum.ude.dao.ITareaDAO;
import com.scrum.ude.service.ProyectoServiceImpl;
import com.scrum.ude.service.TareaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/tareas")
public class ApiTareaController
{
    @Autowired
    private TareaServiceImpl tareaServiceImp;

    @Autowired
    private ITareaDAO tareaDAO;

    @GetMapping(value = "/quick-view/{id}")
    public ModelAndView quickView(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("tarea/quick-view");
        Tarea tarea = tareaServiceImp.buscarPorIdTarea(id);
        mav.addObject("tarea", tarea);
        return mav;
    }

    @GetMapping(value = "/actualizar-estado-tarea/{id}/{status}")
    public String actualizarEstadoTarea(@PathVariable(value = "id") Long id, @PathVariable(value = "status") String status) {
        Tarea tareaBase = tareaServiceImp.buscarPorIdTarea(id);
        tareaBase.setStatus(status);
        tareaDAO.save(tareaBase);
        return "redirect:/verProyectoTarea/" + tareaBase.getProyecto().getId() + "";
    }
}
