package com.perfulandia.cl.microservicio_orden.external;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.perfulandia.cl.microservicio_orden.dto.SucursalDTO;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "servicio-sucursal")
public interface ServicioSucursal {

}
