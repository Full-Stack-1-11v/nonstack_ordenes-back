package com.perfulandia.cl.microservicio_orden.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfulandia.cl.microservicio_orden.model.Orden;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer>{

    //metodo que muestra ordenes por id de orden
    List<Orden> findByIdOrden(int idOrden);

    //metodo que muestra las ordenes que hizo un cliente

    List<Orden> findByIdCliente(int idCliente);

   

    //metodo para ver valor total de una orden

    int findValorTotalByidOrden(int idOrden);



}
