package com.perfulandia.cl.microservicio_orden.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.dto.ProductoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer>{

    //metodo que muestra ordenes por id de orden
    List<Orden> findByIdOrden(int idOrden);

    //metodo que muestra las ordenes que hizo un cliente

    List<Orden> findByIdCliente(int idCliente);

   

    //metodo para ver valor total de una orden

    int findValorTotalByidOrden(int idOrden);

    @Query(value = "SELECT do.id_producto, SUM(do.cantidad) as total_vendido " +
    "FROM orden_detalle do " +
    "GROUP BY do.id_producto " +
    "ORDER BY total_vendido DESC " +
    "LIMIT 3",
    nativeQuery = true)
    List<Object[]> findTopSellingProductIdsNative(@Param("3") int limit);

}   

