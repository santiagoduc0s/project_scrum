package com.scrum.ude.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column
    private String titulo;

    @Column
    private String descripcion;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "curso"
    )
    public List<Capitulo> capitulo;


    public Curso() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Capitulo> getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(List<Capitulo> capitulo) {
        this.capitulo = capitulo;
    }


}
