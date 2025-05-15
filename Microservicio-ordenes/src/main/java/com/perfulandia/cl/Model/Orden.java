package com.perfulandia.cl.Model;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orden {

    @Column(nullable = false)
    private int id_orden;

    @Column(nullable = false)
    private int idCliente;

    @ManyToAny
    @JoinColumn(name = "id_producto")
    @Column(nullable = false)
    private int idProducto;
    
    

}
