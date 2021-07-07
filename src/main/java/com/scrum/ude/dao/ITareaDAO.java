package com.scrum.ude.dao;

import org.springframework.data.repository.CrudRepository;
import com.scrum.ude.entity.Tarea;
public interface ITareaDAO  extends CrudRepository<Tarea, Long> {

}
