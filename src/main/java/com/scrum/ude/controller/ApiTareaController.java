package com.scrum.ude.controller;

import com.scrum.ude.entity.Tarea;
import com.scrum.ude.dao.ITareaDAO;
import com.scrum.ude.service.ProyectoServiceImpl;
import com.scrum.ude.service.TareaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/tareas")
public class ApiTareaController
{
    @Autowired
    private TareaServiceImpl tareaServiceImp;

    @GetMapping(value = "/quick-view/{id}")
    public ModelAndView quickView(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("proyecto/quick-view");
        Tarea tarea = tareaServiceImp.buscarPorIdTarea(id);
        mav.addObject("tarea", tarea);
        return mav;
    }
}
