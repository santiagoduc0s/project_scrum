package com.scrum.ude.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import com.scrum.ude.entity.Pagina;

import java.util.Optional;

public interface IPaginaDAO extends CrudRepository<Pagina,Long> {

//    public Optional<Pagina> findById(Long id);
}
