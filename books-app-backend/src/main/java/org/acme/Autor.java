package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Entity
@Table (name = "autores")
public class Autor extends PanacheEntity {
    
    @Column(name = "nome")
    public String nome;

    @Column(name = "data_nascimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate dataNascimento;
}