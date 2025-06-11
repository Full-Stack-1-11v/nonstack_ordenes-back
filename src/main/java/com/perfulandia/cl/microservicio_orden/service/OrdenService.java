package com.perfulandia.cl.microservicio_orden.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.repository.OrdenRepository;
import com.perfulandia.cl.microservicio_orden.converter.ProductoConverter;
import com.perfulandia.cl.microservicio_orden.dto.ProductoDTO;
import com.perfulandia.cl.microservicio_orden.dto.OrdenRequest;
import com.perfulandia.cl.microservicio_orden.external.ServicioProducto;
import com.perfulandia.cl.microservicio_orden.dto.ItemRequest;
import com.perfulandia.cl.microservicio_orden.model.DetalleOrden;
import com.perfulandia.cl.microservicio_orden.model.Producto;
import feign.FeignException;
import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional; // Necesario para .findById()

@Service
@Transactional // Usando jakarta.transaction.Transactional
public class OrdenService {

    private RestTemplate restTemplate = new RestTemplate();

    private final String PRODUCTOS_API_BASE_URL = "http://localhost:8082/api/v1/productos";

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private ServicioProducto servicioProducto;

    @Autowired
    private ProductoConverter productoConverter; // inyeccion de la clase converter
 

    public Orden crearOrden(OrdenRequest ordenRequest) {
        Orden orden = new Orden();
        orden.setIdCliente(ordenRequest.getIdCliente());
        orden.setIdSucursal(ordenRequest.getIdSucursal());
        orden.setFechaCreacion(ordenRequest.getFechaCreacion() != null ? 
            LocalDate.parse(ordenRequest.getFechaCreacion().toString()) : LocalDate.now());

        int totalOrden = 0; // ¡Cambiado a int!

        if (ordenRequest.getItems() == null || ordenRequest.getItems().isEmpty()) {
            throw new IllegalArgumentException("La orden debe contener al menos un producto.");
        }

        for (ItemRequest itemRequest : ordenRequest.getItems()) { // ¡Usando ItemRequest!
            if ( itemRequest.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad para el producto ID " + itemRequest.getIdProducto() + " debe ser al menos 1.");
            }

            ProductoDTO productoDTO;
            try {
                productoDTO = servicioProducto.getProductoById(itemRequest.getIdProducto());
            } catch (FeignException.NotFound e) {
                throw new RuntimeException("Producto no encontrado con ID: " + itemRequest.getIdProducto() + ". Detalles: " + e.getMessage());
            } catch (FeignException e) {
                throw new RuntimeException("Error al comunicarse con el microservicio de productos para ID " + itemRequest.getIdProducto() + ": " + e.getMessage());
            }

            if (productoDTO == null) {
                throw new RuntimeException("Detalles del producto no disponibles para ID: " + itemRequest.getIdProducto());
            }

            Producto productoDominio = productoConverter.FromDto(productoDTO);

            DetalleOrden detalle = new DetalleOrden();
            detalle.setIdProducto(productoDominio.getIdProducto());
            detalle.setNombreProducto(productoDominio.getNombreProducto());
            detalle.setPrecioUnitario(productoDominio.getPrecioProducto()); // Asigna directamente el int
            detalle.setCantidad(itemRequest.getCantidad());

            // Calcula el subtotal como int
            double subtotal = productoDominio.getPrecioProducto() * itemRequest.getCantidad();
            detalle.setSubtotal(subtotal);

            orden.addDetalle(detalle);
            
            totalOrden += subtotal; // Suma como double
        }

        orden.setTotalOrden(totalOrden);
        return ordenRepository.save(orden);
    }
    


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

    //metodo para mostrar todas las ordenes de un cliente
    public List<Orden> obtenerOrdenesCliente(int idCliente){
        return ordenRepository.findByIdCliente(idCliente);
    }

    // Método para guardar una orden
    // Este método solo guardaría los IDs en la base de datos
    public Orden guardarOrden(Orden orden){
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

    

    @Transactional(readOnly = true) // Solo lectura, no modifica la base de datos
    public List<ProductoDTO> getTopSellingProducts(int limit) {
        // Llama al repositorio para obtener los IDs de producto y sus cantidades vendidas
        // La query nativa devuelve List<Object[]>, donde Object[0] es idProducto y Object[1] es total_vendido
        List<Object[]> topProductIdsAndQuantities = ordenRepository.findTopSellingProductIdsNative(limit);

        if (topProductIdsAndQuantities.isEmpty()) {
            return Collections.emptyList(); // Si no hay datos, devuelve una lista vacía
        }

        // Extrae solo los IDs de los productos para hacer la llamada al microservicio de productos
        List<Integer> productIds = topProductIdsAndQuantities.stream()
            .map(row -> (Integer) row[0]) // Mapea cada Object[] a su primer elemento (el ID del producto)
            .collect(Collectors.toList());

        // Llama al microservicio de productos para obtener el objeto ProductoDTO completo para cada ID
        List<ProductoDTO> topProducts = productIds.stream()
            .map(id -> {
                try {
                    // Endpoint GET /api/v1/productos/{id}
                    return restTemplate.getForObject(PRODUCTOS_API_BASE_URL + "/" + id, ProductoDTO.class);
                } catch (Exception e) {
                    // Loggear el error, manejarlo, o devolver null y filtrarlo
                    System.err.println("Error al obtener producto " + id + " del microservicio de productos: " + e.getMessage());
                    return null; // Si falla, devuelve null para que sea filtrado
                }
            })
            .filter(java.util.Objects::nonNull) // Filtra cualquier producto que no se pudo obtener (fue null)
            .collect(Collectors.toList());

        return topProducts;
    }

    //metodo para patch una orden
    public Orden patchOrden(int idOrden, Orden orden){
        if (ordenRepository.existsById(idOrden)){
            orden.setIdOrden(idOrden);
            return ordenRepository.save(orden);
        }
        return null;
    }

    
}