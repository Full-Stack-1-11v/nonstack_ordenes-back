package com.perfulandia.cl.microservicio_orden.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Entity
@Table(name = "orden_detalle") // Nueva tabla para los detalles de la orden
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleOrden {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetalle;

    // Relación ManyToOne con la entidad Orden (dentro del mismo microservicio)
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading para evitar cargar la Orden completa innecesariamente
    @JoinColumn(name = "id_orden", nullable = false) // Columna para la clave foránea a la tabla de Ordenes
    private Orden orden;

    // ID del producto del microservicio de Productos (NO un @ManyToOne a la entidad Producto)
    @Column(name = "id_producto_externo", nullable = false)
    private int idProductoExterno; // Almacena el ID del producto del otro microservicio

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario; // Podrías almacenar el precio en el momento de la compra
}


    


