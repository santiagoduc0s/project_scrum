package com.scrum.ude.service.Interfaces;

import java.util.List;

import com.scrum.ude.entity.Capitulo;
//import com.scrum.ude.entity.Capitulo;
import com.scrum.ude.entity.Pagina;

public interface ICapitulo {

	
	Capitulo buscarPaginas(Long id);

	List<Capitulo> buscarPaginas();


}
