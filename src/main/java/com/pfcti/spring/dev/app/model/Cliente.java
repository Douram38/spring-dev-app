package com.pfcti.spring.dev.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="TCLIENTE")
public class Cliente {
    @Id
    private int id;
    @Column (name="nombre")
    private String nombre;
    @Column (name="apellidos",length = 30)
    private String apellidos;
    @Column (name="cedula", columnDefinition = "varchar(15)")
    private String cedula;
    @Column (name="Telefono")
    private String Telefono;

}
