package com.perfulandia.cl.microservicio_orden.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.repository.OrdenRepository;

import java.util.List;

@Service
@Transactional
public class OrdenService {

    @Autowired //inyeccion de dependencia ordenRepository
    private OrdenRepository ordenRepository; 


    //metodo para mostrar todas las ordenes
    public List<Orden> mostrarOrdenes(){
        return ordenRepository.findAll();
    }

    //metodo para guardar una orden 
    public Orden guardarOrden(Orden orden){
        return ordenRepository.save(orden);
    }

    //metodo para eliminar una orden
    public boolean eliminarOrden(int idOrden){
        
         if (ordenRepository.existsById(idOrden)){
            ordenRepository.deleteById(idOrden);
            System.out.println("Orden eliminada correctamente");
            return true;
         }
         System.out.println("La orden que desea eliminar no existe");
         return false;
         }

         //metodo para actualizar una orden
         public Orden actualizarOrden(Orden orden, int idOrden){
            if (ordenRepository.existsById(idOrden)){
                orden.setIdOrden(idOrden);
                return ordenRepository.save(orden);
            }
            return null;
         }

}
