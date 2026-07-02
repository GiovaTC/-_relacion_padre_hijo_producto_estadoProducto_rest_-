package com.ejemplo.producto.controller;

import com.ejemplo.producto.entity.Producto;
import com.ejemplo.producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    ProductoService service;

    @PostMapping
    public ResponseEntity<String> guardar(
            @RequestBody Producto producto) {

        service.guardar(producto);

        return ResponseEntity.ok(
                "producto registrado correctamente");
    }
}
