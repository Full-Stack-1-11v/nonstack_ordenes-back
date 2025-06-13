package com.perfulandia.cl.microservicio_orden.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.service.OrdenService;
import com.perfulandia.cl.microservicio_orden.dto.OrdenRequest;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.perfulandia.cl.microservicio_orden.dto.ProductoDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PatchMapping;



@RestController
@RequestMapping("/api/v1/orden")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;


    //metodo para llamar a una orden por su id
    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerOrden(@PathVariable int id){
        Orden orden = ordenService.obtenerOrden(id);
        if (orden == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orden);
    }
    //metodo para mostrar todas las ordenes de un cliente
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Orden>> obtenerOrdenesPorCliente(@PathVariable int idCliente) {
        List<Orden> ordenes = ordenService.obtenerOrdenesCliente(idCliente);
        if (ordenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ordenes);
    }
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
    @PostMapping("/guardarOrdenSinDetalle") 
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
    public ResponseEntity<Void> eliminarOrden(@PathVariable int idOrden){ // Aquí está el detalle
        boolean eliminado = ordenService.eliminarOrden(idOrden);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok().build(); 
        
    }
    @PutMapping("/actualizar/{idOrden}")
    public ResponseEntity<Orden> actualizarOrden(@RequestBody Orden orden,@PathVariable int idOrden){

        Orden ordenActualizada = ordenService.actualizarOrden(orden, idOrden);
        if (ordenActualizada == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ordenActualizada, HttpStatus.OK);

    }
    
    @PostMapping("/guardar") // Mapea solicitudes HTTP POST a /api/ordenes
    public ResponseEntity<?> crearOrden(@Valid @RequestBody OrdenRequest request) {
        try {
            Orden nuevaOrden = ordenService.crearOrden(request);
            // Si la orden se crea con éxito, devuelve la orden y un estado 201 Created
            return new ResponseEntity<>(nuevaOrden, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Si hay un error de validación de negocio (ej. cantidad <= 0, lista vacía de productos),
            // devuelve un estado 400 Bad Request con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            // Este catch es más general y capturaría errores del servicio como
            // "Producto no encontrado" o problemas de comunicación con el microservicio de productos.
            // Devuelve un estado 500 Internal Server Error.
            return new ResponseEntity<>("Error al procesar la orden: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   // @GetMapping("/top-vendidos") // Método GET para obtener recursos
   // public ResponseEntity<List<ProductoDTO>> getTopSellingProducts(
   //         @RequestParam(defaultValue = "1") int limit) { // @RequestParam para obtener el parámetro 'limit' de la URL
   //     List<ProductoDTO> topProducts = ordenService.getTopSellingProducts(limit);
   //     if (topProducts.isEmpty()) {
   //         return ResponseEntity.noContent().build(); // Devuelve 204 No Content si no hay resultados
   //     }
   //     return ResponseEntity.ok(topProducts); // Devuelve 200 OK con la lista de productos
  //  }
    
    @PatchMapping("/actualizar/{idOrden}")
    public ResponseEntity<Orden> patchOrden(@PathVariable int idOrden, @RequestBody Orden orden){
        Orden ordenActualizada = ordenService.patchOrden(idOrden, orden);
        if (ordenActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ordenActualizada);
    }
}
