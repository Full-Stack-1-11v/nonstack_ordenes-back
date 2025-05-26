package com.perfulandia.cl.microservicio_orden.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;



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
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading para evitar cargar la Orden completa innecesariamente
    @JoinColumn(name = "id_orden", nullable = false) // Columna para la clave foránea a la tabla de Ordenes
    private Orden orden;

    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario; // Podrías almacenar el precio en el momento de la compra

    @Column(name = "subtotal", nullable = false)
    private double subtotal;
}


    


