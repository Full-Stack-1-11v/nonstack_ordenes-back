package com.perfulandia.cl.microservicio_orden.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.service.OrdenService;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
@RequestMapping("/orden")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    //metodo para mostrar todas las ordenes
    @GetMapping("/listar")
    public ResponseEntity<List<Orden>> listar() {

        List<Orden> ordenes = ordenService.mostrarOrdenes();
        if (ordenes.isEmpty()) {
            return ResponseEntity.noContent().build(); //si no hay ordenes, devuelvo un 204 xd
        }
        return ResponseEntity.ok(ordenes); //si hay ordenes, se devuelve un 200 osea un todo bien
    }   
    //metodo para guardar una orden
    @PostMapping("/guardar")
    public ResponseEntity<Orden> guardarOrden(@RequestBody Orden orden){

        Orden ordenGuardada = ordenService.guardarOrden(orden);  //guardo la orden
        if (ordenGuardada == null){
            return ResponseEntity.badRequest().build(); //si no se guardo, devuelvo un 400
        }
        System.out.println("Orden guardada correctamente"); //si se guardo, devuelvo un 200 y un mensaje que confirma
        return ResponseEntity.ok(ordenGuardada);    //devuelvo la orden guardada
    }

    //metodo para eliminar una orden 
    @DeleteMapping("/eliminar/{idOrden}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable int id){ // Aquí está el detalle
        boolean eliminado = ordenService.eliminarOrden(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build(); // Aquí podemos mejorar la convención HTTP
    }
        
    
}
