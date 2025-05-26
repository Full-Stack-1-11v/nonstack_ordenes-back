package com.perfulandia.cl.microservicio_orden.dto;

import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrdenRequest {

    @NotNull(message = "El ID del cliente no puede ser nulo.")
    @Min(value = 1, message = "El ID del cliente debe ser un número positivo.")
    private int idCliente;

    @NotNull(message = "El ID de la sucursal no puede ser nulo.")
    @Min(value = 1, message = "El ID de la sucursal debe ser un número positivo.")
    private int idSucursal;

    @NotEmpty(message = "La orden debe contener al menos un producto.")
    private List<ItemRequest> items;

    public OrdenRequest() {}

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdSucursal() { return idSucursal; }
    public void setIdSucursal(int idSucursal) { this.idSucursal = idSucursal; }

    public List<ItemRequest> getItems() { return items; }
    public void setItems(List<ItemRequest> items) { this.items = items; }

    

}
