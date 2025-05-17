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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orden")
public class Orden {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrden;
    
    @Column(name = "id_cliente")
    private int idCliente;

    @Column(name = "id_producto")
    private int idProducto;

    

    

}
