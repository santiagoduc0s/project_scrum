package com.scrum.ude.service.Interfaces;

import com.scrum.ude.entity.Opcion;
import com.scrum.ude.entity.Pregunta;

public interface ICuestionario {

	Pregunta buscarPregunta(Long id);

	Opcion buscarOpcion(Long id);

	boolean respuestaCorrecta(Long idPregunta, Long idOpcion);

}
