package com.scrum.ude.controller;

import com.scrum.ude.service.CuestionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/cuestionario")
public class ApiCuestionarioController {

    @Autowired
    private CuestionarioService cuestionarioService;

    @GetMapping("/validar/{idPregunta}/{idOpcion}")
    public ResponseEntity<?> validarPregunta(@PathVariable Long idPregunta, @PathVariable Long idOpcion) {

        if (cuestionarioService.respuestaCorrecta(idPregunta, idOpcion)) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"code\": \"1\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body("{\"code\": \"0\"}");
    }

}
