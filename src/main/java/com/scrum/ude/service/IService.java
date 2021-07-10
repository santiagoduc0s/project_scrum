package com.scrum.ude.service;

import java.util.List;

import com.scrum.ude.entity.CodigoRegistro;
import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.entity.Tarea;
import com.scrum.ude.entity.Usuario;

public interface IService {

	Usuario findOne(String user);

	void agregarUsuario(Usuario usuario);

	Usuario buscarPorId(Long id);
	//Usuario buscarPorCedula(Long cedula);

	CodigoRegistro findCodigo(String codigo);

	Proyecto buscarPorIdProyecto(Long id);
	List<Proyecto> buscarProyectoPorUsuario(Long id);

	Proyecto buscarProyectoPorUsuarioWithTitulo(Long id, String titulo);

	Tarea buscarPorNombreTarea(String nombre);

	Tarea buscarPorIdTarea(Long id);

}
