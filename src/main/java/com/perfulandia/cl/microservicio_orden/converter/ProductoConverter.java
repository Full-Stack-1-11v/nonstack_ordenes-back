package com.perfulandia.cl.microservicio_orden.converter;
import com.perfulandia.cl.microservicio_orden.model.Producto;

import org.springframework.stereotype.Component;

import com.perfulandia.cl.microservicio_orden.dto.ProductoDTO;

@Component
public class ProductoConverter {


    public Producto FromDto(ProductoDTO productoDTO){
        if(productoDTO == null){
            return null;
        }
        Producto productoOrden =  new Producto();
        productoOrden.setIdProducto(productoDTO.getIdProducto());
        productoOrden.setNombreProducto(productoDTO.getNombreProducto());
        productoOrden.setPrecioProducto(productoDTO.getPrecioProducto());
        productoOrden.setDescripcionProducto(productoDTO.getDescripcionProducto());
        productoOrden.setIdCategoriaProducto(productoDTO.getIdCategoriaProducto());
        productoOrden.setStockProducto(productoDTO.getStockProducto());
        return productoOrden;
    }   

}

