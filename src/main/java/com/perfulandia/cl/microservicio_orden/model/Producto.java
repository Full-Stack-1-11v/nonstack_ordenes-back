package com.perfulandia.cl.microservicio_orden.model;

import lombok.Data;

@Data
public class Producto {

    private Integer idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Double precioProducto;
    private Integer idCategoriaProducto;
    private Integer stockProducto;
}
