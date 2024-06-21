package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "livros")
public class Book extends PanacheEntity {
    
    @Column(name = "titulo")
    public String titulo;
    
    @Column(name = "autor")
    public String autor;
    
    @Column(name = "ano_publicacao")
    public int anoPublicacao;

    @Column(name = "numero_de_paginas")
    public int numeroDePaginas;
}