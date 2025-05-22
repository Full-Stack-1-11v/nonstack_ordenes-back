package com.perfulandia.cl.microservicio_orden.external;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.perfulandia.cl.microservicio_orden.dto.SucursalDTO;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;



@FeignClient(name = "servicio-sucursal", url = "localhost:8082/sucursales")
public interface ServicioSucursal {

    @GetMapping("/sucursal/{id}") //Obtener sucursal por id
    public SucursalDTO getSucursalById(@PathVariable int idSucursal);

    @GetMapping("/sucursal/all") //Obtener todas las sucursales
    public List<SucursalDTO> getAllSucursales();





}
