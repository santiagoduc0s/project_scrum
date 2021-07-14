package com.scrum.ude.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Capitulo {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@OneToOne
private  Pagina pagina;
//@Column
//private Proyecto  proyecto;

@Column
private String nombre;


public Capitulo() {
	
}

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


public String getNombre() {
	return nombre;
}


public void setNombre(String nombre) {
	this.nombre = nombre;
}

}
