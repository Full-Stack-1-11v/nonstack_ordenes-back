package com.perfulandia.cl.microservicio_orden.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.repository.OrdenRepository;
import com.perfulandia.cl.microservicio_orden.service.OrdenService;
import java.time.LocalDate;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import com.perfulandia.cl.microservicio_orden.external.ServicioProducto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
@SpringBootTest
public class OrdenServiceTest {

    @Autowired
    private OrdenService ordenService;

    @MockitoBean
    private OrdenRepository ordenRepository;


    @MockitoBean
    private ServicioProducto servicioProducto;


    @Test
    public void TestMostrarOrdenes() {

        when(ordenRepository.findAll()).thenReturn(List.of(new Orden(1,1,1, LocalDate.now(), 100.0, null)));

        List<Orden> ordenes = ordenService.mostrarOrdenes();

        assertNotNull(ordenes);
        assertEquals(ordenes.size(), 1);


    }

    @Test
    public void TestObtenerOrden(){
        when(ordenRepository.findById(1)).thenReturn(java.util.Optional.of(new Orden(1,1,1, LocalDate.now(), 100.0, null)));

        Orden orden = ordenService.obtenerOrden(1);

        assertNotNull(orden);
        assertEquals(orden.getIdOrden(), 1);
        assertEquals(orden.getIdCliente(), 1);
        assertEquals(orden.getTotalOrden(), 100.0);
        
    }


    @Test
    public void TestObtenerOrdenesCliente() {
        when(ordenRepository.findByIdCliente(1)).thenReturn(List.of(new Orden(1, 11, 1, LocalDate.now(), 100.0, null)));

        List<Orden> ordenes = ordenService.obtenerOrdenesCliente(1);

        assertNotNull(ordenes);
        assertEquals(1,ordenes.size());
        assertEquals(ordenes.get(0).getIdCliente(), 11);

    }

    @Test
    public void TestGuardarOrden() {
        Orden orden = new Orden(1, 1, 1, LocalDate.now(), 100.0, null);
        when(ordenRepository.save(orden)).thenReturn(orden);

        Orden ordenGuardada = ordenService.guardarOrden(orden);

        assertNotNull(ordenGuardada);
        assertEquals(ordenGuardada.getIdOrden(), 1);
        assertEquals(ordenGuardada.getIdCliente(), 1);
        assertEquals(ordenGuardada.getTotalOrden(), 100.0);
    }


    @Test
    public void TestEliminarOrdenExiste() {
        int idOrden = 1;
        when(ordenRepository.existsById(idOrden)).thenReturn(true);

        boolean resultado = ordenService.eliminarOrden(idOrden);

        assertEquals(true, resultado);
    }

    @Test
    public void TestEliminarOrdenNoExiste() {
        int idOrden = 1;
        when(ordenRepository.existsById(idOrden)).thenReturn(false);

        boolean resultado = ordenService.eliminarOrden(idOrden);

        assertEquals(false, resultado);

    }   
    
    @Test
    public void TestEliminarOrden(){

        int idOrden = 1;
        
        doNothing().when(ordenRepository).deleteById(1);

        ordenService.eliminarOrden(idOrden);

        verify(ordenRepository, times(1)).deleteById(idOrden);

       
    }



}
