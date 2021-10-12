package com.scrum.ude.service.Interfaces;

import java.util.List;

import com.scrum.ude.entity.Pagina;

public interface IPagina {

	Pagina obtenerContenido(Long id);

	List<Pagina> obtenerPaginas();

}
