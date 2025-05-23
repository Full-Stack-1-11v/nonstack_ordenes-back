package com.perfulandia.cl.microservicio_orden.model;
import com.perfulandia.cl.microservicio_orden.dto.ClienteDTO;
import com.perfulandia.cl.microservicio_orden.dto.ProductoDTO;
import com.perfulandia.cl.microservicio_orden.dto.SucursalDTO;

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
    
    @Column(name = "id_cliente", nullable = false)
    private ClienteDTO idCliente;

    @Column(name = "id_producto", nullable = false)
    private ProductoDTO idProducto;

    @Column(name = "id_sucursal" ,nullable = false)
    private SucursalDTO idSucursal;


    

    

}
