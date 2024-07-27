package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.*;

@Entity
@Table(name = "livros")
public class Book extends PanacheEntity {
    
    @Column(name = "titulo")
    public String titulo;
    
    @Column(name = "ano_publicacao")
    public int anoPublicacao;

    @Column(name = "numero_de_paginas")
    public int numeroDePaginas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    public Autor autor;
}