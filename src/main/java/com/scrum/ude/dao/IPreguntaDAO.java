package com.scrum.ude.dao;

import org.springframework.data.repository.CrudRepository;

import com.scrum.ude.entity.CodigoRegistro;
import com.scrum.ude.entity.Pregunta;

public interface IPreguntaDAO  extends CrudRepository<Pregunta, Long> {

}
