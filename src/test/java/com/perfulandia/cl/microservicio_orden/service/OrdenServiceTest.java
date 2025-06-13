package com.perfulandia.cl.microservicio_orden.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.repository.OrdenRepository;
import com.perfulandia.cl.microservicio_orden.service.OrdenService;
import java.time.LocalDate;
import java.util.List;
import com.perfulandia.cl.microservicio_orden.external.ServicioProducto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrdenServiceTest {

    @Autowired
    private OrdenService ordenService;

    @MockBean
    private OrdenRepository ordenRepository;


    @MockBean
    private ServicioProducto servicioProducto;


    @Test
    public void TestMostrarOrdenes() {

        when(ordenRepository.findAll()).thenReturn(List.of(new Orden(1,1,1, LocalDate.now(), 100.0, null)));

        List<Orden> ordenes = ordenService.mostrarOrdenes();

        assertNotNull(ordenes);
        assertEquals(ordenes.size(), 1);


    }
}
