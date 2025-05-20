package com.perfulandia.cl.microservicio_orden.external;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.perfulandia.cl.microservicio_orden.dto.ClienteDTO;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "servicio-cliente", url = "https://servicio-cliente")
public interface ServicioCliente {





}
