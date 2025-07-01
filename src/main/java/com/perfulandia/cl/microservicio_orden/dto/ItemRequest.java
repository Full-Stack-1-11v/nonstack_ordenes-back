package com.perfulandia.cl.microservicio_orden.dto;

import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ItemRequest {

    @NotNull(message = "El ID del producto no puede ser nulo.")
    @Min(value = 1, message = "El ID del producto debe ser un número positivo.")
    private Integer idProducto;

    @NotNull(message = "La cantidad no puede ser nula.")
    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    private int cantidad;

    public ItemRequest() {}

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

}
