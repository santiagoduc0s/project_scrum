package com.scrum.ude.controller;

import com.scrum.ude.dao.ITareaDAO;
import com.scrum.ude.entity.Tarea;
import com.scrum.ude.service.ProyectoServiceImpl;
import com.scrum.ude.service.TareaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ApiTareaController {

    @Autowired
    private TareaServiceImpl tareaServiceImp;

    @GetMapping(value = "/api/tarea/quick-view/{id}")
    public Tarea quickView(@PathVariable(value = "id") Long id) {
        return tareaServiceImp.buscarPorIdTarea(id);
    }
}
