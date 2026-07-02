package com.ejemplo.producto.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "estado_producto")
public class EstadoProducto {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estado;
    private LocalDate fechaRegistro;
    private String observacion;
    private String responsable;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
