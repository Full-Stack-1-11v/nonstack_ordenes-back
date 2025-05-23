package com.perfulandia.cl.microservicio_orden.dto;
import lombok.Data;

@Data
public class ProductoDTO {
    private Integer idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Double precioProducto;
    private Integer idCategoriaProducto;
    private Integer stockProducto;

}
