package com.ejemplo.producto.service;

import com.ejemplo.producto.entity.Producto;
import com.ejemplo.producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository repository;

    public Producto guardar(Producto producto) {

        producto.getEstados().forEach(estado -> {
            estado.setProducto(producto);
        });

        return repository.save(producto);
    }
}
