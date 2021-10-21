package com.scrum.ude.service.Interfaces;

import java.util.List;

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

	Boolean ExisteUsuario(String userName);

	Boolean ExisteMail(String email);

	Usuario buscarPorMail(String email);

	List<Usuario> buscarProyectosoVinculadosPorUsuario(Long id);

	Usuario buscarUsuarioPorUsername(String username);


	//Curso codigoCurso(int codigo);

	//Inscripto buscarUsuarioIncripto(Long id);

}
