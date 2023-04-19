package com.pfcti.spring.dev.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Inversion {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    private String numero;
    private String  nomenclatura;

    @ManyToOne
    @JoinColumn(name="cliente_id",referencedColumnName = "id")
    private Cliente cliente;

}
