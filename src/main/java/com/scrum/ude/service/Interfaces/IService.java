package com.scrum.ude.service.Interfaces;

import com.scrum.ude.entity.Usuario;

public interface IService {

	Usuario findOne(String user);

	void agregarUsuario(Usuario usuario);

	Usuario buscarPorId(Long id);
	//Usuario buscarPorCedula(Long cedula);

	//CodigoRegistro findCodigo(String codigo);

//	Proyecto buscarPorIdProyecto(Long id);
//	List<Proyecto> buscarProyectoPorUsuario(Long id);
//
//	Proyecto buscarProyectoPorUsuarioWithTitulo(Long id, String titulo);

//	Tarea buscarPorNombreTarea(String nombre);
//
//	Tarea buscarPorIdTarea(Long id);

	Usuario buscarPorCedula(Long cedula);


	//Curso codigoCurso(int codigo);

	//Inscripto buscarUsuarioIncripto(Long id);

}
