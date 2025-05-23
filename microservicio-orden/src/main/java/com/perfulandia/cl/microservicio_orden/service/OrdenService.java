package com.perfulandia.cl.microservicio_orden.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; 
import org.springframework.beans.factory.annotation.Autowired;

import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.repository.OrdenRepository;

// Importaciones de DTOs y FeignClients
import com.perfulandia.cl.microservicio_orden.dto.ClienteDTO;
import com.perfulandia.cl.microservicio_orden.dto.ProductoDTO;
import com.perfulandia.cl.microservicio_orden.dto.SucursalDTO;   
import com.perfulandia.cl.microservicio_orden.dto.OrdenDetalleDTO; // DTO que enviamos como respuesta

import com.perfulandia.cl.microservicio_orden.external.ServicioCliente;
import com.perfulandia.cl.microservicio_orden.external.ServicioProducto;
import com.perfulandia.cl.microservicio_orden.external.ServicioSucursal;   

import java.util.List;
import java.util.Optional; // Necesario para .findById()

@Service
@Transactional // Usando jakarta.transaction.Transactional
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private ServicioCliente servicioCliente; // Inyección del FeignClient de Cliente
    @Autowired
    private ServicioProducto servicioProducto; // Inyección del FeignClient de Producto
    @Autowired
    private ServicioSucursal servicioSucursal; // Inyección del FeignClient de Sucursal


    // Método para mostrar todas las órdenes
    public List<Orden> mostrarOrdenes(){
        return ordenRepository.findAll();
    }

    //metodo para obtener una orden por su id
    public Orden obtenerOrden(int id){
        Optional<Orden> orden = ordenRepository.findById(id);
    if (orden.isPresent()){
        return orden.get();
    }
        return null;
    }

    // Método para guardar una orden
    // Este método solo guardaría los IDs en la base de datos
    public Orden guardarOrden(Orden orden){
        // Aquí no necesitas llamar a los FeignClients si solo guardas los IDs
        // La validación de que el ID del cliente, producto y sucursal existen
        // se haría antes de llamar a este método o en un método de creación más complejo.
        return ordenRepository.save(orden);
    }

    // Método para eliminar una orden
    public boolean eliminarOrden(int idOrden){
        if (ordenRepository.existsById(idOrden)){
            ordenRepository.deleteById(idOrden);
            System.out.println("Orden eliminada correctamente");
            return true;
        }
        System.out.println("La orden que desea eliminar no existe");
        return false;
    }

    // Método para actualizar una orden
    public Orden actualizarOrden(Orden orden, int idOrden){
        if (ordenRepository.existsById(idOrden)){
            orden.setIdOrden(idOrden);
            return ordenRepository.save(orden);
        }
        return null;
    }

    

    /// Método para Obtener el Detalle Completo de una Orden

    
    public OrdenDetalleDTO obtenerDetalleOrden(int idOrden) {
        // 1. Buscar la orden en tu propia base de datos
        Optional<Orden> optionalOrden = ordenRepository.findById(idOrden);
        if (optionalOrden.isEmpty()) {
            // Manejar el caso donde la orden no existe en tu DB
            throw new RuntimeException("Orden con ID " + idOrden + " no encontrada en la base de datos");
        }
        Orden orden = optionalOrden.get();

        // 2. Usar los FeignClients para obtener detalles de otros microservicios
        ClienteDTO cliente = null;
        try {
            // Asegúrate de que el ID del cliente en tu entidad Orden sea compatible con int
            // Si tu Orden.id_cliente es Long, conviértelo a int si el FeignClient lo espera.
            cliente = servicioCliente.getClienteById(orden.getIdCliente());
            if (cliente == null) {
                // Si el servicio de cliente devuelve null o no lanza excepción para no encontrado
                System.out.println("Advertencia: Cliente con ID " + orden.getIdCliente() + " no encontrado en la base de datos");
                // Opcional: lanzar una excepción si es un requisito que el cliente siempre exista
                // throw new RuntimeException("Cliente asociado a la orden no encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener cliente ID " + orden.getIdCliente() + ": " + e.getMessage());
            // Manejo de errores: Cliente no disponible o error de red
            // Dependiendo de tu lógica de negocio, podrías devolver un DTO parcial
            // o lanzar una excepción específica.
        }

        ProductoDTO producto = null;
        try {
            producto = servicioProducto.getProductoById(orden.getIdProducto());
            if (producto == null) {
                System.out.println("Advertencia: Producto con ID " + orden.getIdProducto() + " no encontrado en la base de datos");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener producto ID " + orden.getIdProducto() + ": " + e.getMessage());
        }

        SucursalDTO sucursal = null;
        try {
            sucursal = servicioSucursal.getSucursalById(orden.getIdSucursal());
            if (sucursal == null) {
                System.out.println("Advertencia: Sucursal con ID " + orden.getIdSucursal() + " no encontrada en la base de datos.");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener sucursal ID " + orden.getIdSucursal() + ": " + e.getMessage());
        }


        // 3. Construir el DTO de respuesta (OrdenDetalleDTO)
        OrdenDetalleDTO detalle = new OrdenDetalleDTO();
        detalle.setIdOrden(orden.getIdOrden());
        detalle.setFechaCreacion(orden.getFechaCreacion()); // Asumiendo que tu entidad Orden tiene fecha
        detalle.setTotal(orden.getTotalOrden());                   // Asumiendo que tu entidad Orden tiene total

        // Asignar los DTOs de otros servicios (pueden ser null si no se encontraron o hubo error)
        detalle.setCliente(cliente);
        detalle.setProducto(producto);
        detalle.setSucursal(sucursal);

        return detalle;
    }
}