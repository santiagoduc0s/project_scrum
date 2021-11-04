package com.scrum.ude.repository;

import com.scrum.ude.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    public List<Proyecto> findAllByTituloOrDescripcionContaining(String titulo, String descripcion);
}
