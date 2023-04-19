package com.pfcti.spring.dev.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
@Table(name="TCLIENTE")
public class Cliente {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    @Column (name="nombre")
    private String nombre;
    @Column (name="apellidos",length = 30)
    private String apellidos;
    @Column (name="cedula", columnDefinition = "varchar(15)")
    private String cedula;
    @Column (name="Telefono")
    private String Telefono;

    @OneToMany(mappedBy = "cliente") // cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)

    private List<Direccion> direcciones;

}
