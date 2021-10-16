package com.scrum.ude.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pregunta")
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // pagina
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pagina_id")
    private Pagina pagina;

    // opciones
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "pregunta"
    )
    private List<Opcion> opciones = new ArrayList<>();

    // opcion correcta
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "opcion_correcta_id")
    private Opcion opcionCorrecta;

    @Column(columnDefinition = "text")
    private String pregunta;

    // ------------------------

    public Pregunta() {}

    // ------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }

    public Opcion getOpcionCorrecta() {
        return opcionCorrecta;
    }

    public void setOpcionCorrecta(Opcion opcionCorrecta) {
        this.opcionCorrecta = opcionCorrecta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
}
