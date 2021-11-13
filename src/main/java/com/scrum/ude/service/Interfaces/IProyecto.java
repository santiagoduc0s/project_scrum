package com.scrum.ude.service.Interfaces;

import java.util.List;

import com.scrum.ude.entity.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProyecto {

	Proyecto buscarProyectoPorUsuarioWithTitulo(Long id, String titulo);

	List<Proyecto> buscarProyectoPorUsuario(Long id);

	Proyecto buscarPorIdProyecto(Long id);

	Proyecto buscarPorCodigoProyecto(String codigo);

	List<Proyecto> buscarPorusuarioProyectos(Long id);

	List<Proyecto> buscarTodosLosProyectos();

	public Page<Proyecto> getAll(Pageable pageable);

	//public List<Proyecto> buscarProyectosPorUsuario(Long id);
}
