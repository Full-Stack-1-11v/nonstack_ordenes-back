package com.perfulandia.cl.microservicio_orden.external;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.perfulandia.cl.microservicio_orden.dto.ProductoDTO;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "PRODUCTIOS-API", url= "${external.servicio-producto.api.base-url}")
public interface ServicioProducto {

    @GetMapping("/producto/{id}")
    //public ProductoDTO getProductoById(@PathVariable int id);

    ProductoDTO getProductoById(@PathVariable("id") int idProducto);

}
