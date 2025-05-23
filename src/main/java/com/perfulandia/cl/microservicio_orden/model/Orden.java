package com.perfulandia.cl.microservicio_orden.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orden")
public class Orden {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrden;
    
    @Column(name = "id_cliente", nullable = false)
    private int idCliente;

    @Column(name = "id_producto", nullable = false)
    private int idProducto;

    @Column(name = "id_sucursal" ,nullable = false)
    private int idSucursal;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    
    @Column(name = "total_orden", nullable = false)
    private double totalOrden;

     // ¡Nueva relación OneToMany con DetalleOrden!
     @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
     // `mappedBy` indica el campo en DetalleOrden que mapea la relación (private Orden orden)
     // `cascade = CascadeType.ALL` significa que si persistes/eliminas una Orden, sus Detalles se persistirán/eliminarán
     // `orphanRemoval = true` si quieres que un detalle se elimine si se quita de la lista de la orden
     private List<DetalleOrden> detalles; // Una orden tiene muchos detalles

    

    

}
