package com.scrum.ude.entity;

import javax.persistence.*;

@Entity
@Table(name = "opcion")
public class Opcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pregunta_id")
    private Pregunta pregunta;

    @Column(columnDefinition = "text")
    private String contenido;

    // -----------------------

    public Opcion() {}

    // -----------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
