package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "autores")
public class Autor extends PanacheEntity {
    
    @Column(name = "nome")
    public String nome;

    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    public Date dataNascimento;
}