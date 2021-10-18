package com.scrum.ude.dao;

import org.springframework.data.repository.CrudRepository;

import com.scrum.ude.entity.CodigoRegistro;
import com.scrum.ude.entity.Opcion;

public interface IOpcionDAO extends CrudRepository<Opcion, Long> {

}
