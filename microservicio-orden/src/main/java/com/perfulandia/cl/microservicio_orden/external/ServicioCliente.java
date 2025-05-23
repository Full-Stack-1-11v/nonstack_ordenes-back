package com.perfulandia.cl.microservicio_orden.external;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.perfulandia.cl.microservicio_orden.dto.ClienteDTO;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "CLIENTES-API", url = "${external.servicio-cliente.api.base-url}")
public interface ServicioCliente {

    @GetMapping("/cliente/{id}")
    public ClienteDTO getClienteById(@PathVariable int idCliente);





}
