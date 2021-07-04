package com.scrum.ude.service;

import com.scrum.ude.entity.CodigoRegistro;
import com.scrum.ude.entity.Usuario;

public interface IService {

	Usuario findOne(String user);

	void agregarUsuario(Usuario usuario);

	Usuario buscarPorId(Long id);
	Usuario buscarPorCedula(Long cedula);

	CodigoRegistro findCodigo(String codigo);

}
