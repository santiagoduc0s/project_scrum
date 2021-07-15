package com.scrum.ude.service.Interfaces;

import java.util.List;

import com.scrum.ude.entity.Proyecto;

public interface IProyecto {

	Proyecto buscarProyectoPorUsuarioWithTitulo(Long id, String titulo);

	List<Proyecto> buscarProyectoPorUsuario(Long id);

	Proyecto buscarPorIdProyecto(Long id);
}
