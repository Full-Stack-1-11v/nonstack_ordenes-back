package com.perfulandia.cl.microservicio_orden.external;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.perfulandia.cl.microservicio_orden.dto.SucursalDTO;
import org.springframework.web.bind.annotation.PathVariable;


@FeingClient(name = "servicio-sucursal", url = url del luciano)
public interface ServicioSucursal {

}
