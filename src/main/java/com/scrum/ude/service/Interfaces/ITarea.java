package com.scrum.ude.service.Interfaces;

import com.scrum.ude.entity.Tarea;

public interface ITarea {

	Tarea buscarPorNombreTarea(String nombre);

	Tarea buscarPorIdTarea(Long id);

	Tarea actualizarTarea(Tarea tarea);

}
