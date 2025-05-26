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
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @Column(name = "id_sucursal" ,nullable = false)
    private int idSucursal;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    
    @Column(name = "total_orden", nullable = false)
    private double totalOrden;

     // ¡Nueva relación OneToMany con DetalleOrden!
     @JsonManagedReference
     @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
     private List<DetalleOrden> detalles = new ArrayList<>();

     public void addDetalle(DetalleOrden detalle) {
        // Añade el detalle a la lista de detalles de esta orden
        this.detalles.add(detalle);
        // Establece la referencia inversa, vinculando el detalle de vuelta a esta orden
        detalle.setOrden(this);
    }
    

    

}
