package com.ejemplo.producto.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private String categoria;

    @OneToMany(
            mappedBy = "producto",
            cascade = CascadeType.ALL
    )
    private List<EstadoProducto> estados = new ArrayList<>();
}
