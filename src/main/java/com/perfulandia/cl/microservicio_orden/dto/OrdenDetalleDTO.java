package com.perfulandia.cl.microservicio_orden.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date; // Si usas fechas

@Data // Anotación de Lombok para getters, setters, etc.
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDetalleDTO {
    private int idOrden;
    private Date fechaCreacion; // Ejemplo de campo de la Orden
    private double total;                 // Ejemplo de campo de la Orden

    // DTO del cliente
    private ProductoDTO producto; // DTO del producto
 

    
}
