package com.perfulandia.cl.microservicio_orden.controller;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.service.OrdenService;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Optional;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@WebMvcTest(OrdenController.class)
public class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrdenService ordenService;

    @Autowired
    private OrdenController ordenController;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test //prueba unitaria de el metodo ObtenerOrden
    public void testObtenerOrden() throws Exception {
        Orden orden = new Orden(1, 1, 1, null, 100.0, null);
        when(ordenService.obtenerOrden(1)).thenReturn(orden);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orden/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idOrden").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCliente").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idSucursal").value(1));
    }

    @Test //prueba unitaria de el metodo obtenerOrdenesPorCliente
    public void testObtenerOrdenesPorCliente() throws Exception {

        when(ordenService.obtenerOrdenesCliente(1)).thenReturn(List.of(new Orden(1, 1, 1, null, 100.0, null)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orden/cliente/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idOrden").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idCliente").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idSucursal").value(1));
    }

    @Test //prueba unitaria de el metodo listar
    public void testListar() throws Exception {
        when(ordenService.mostrarOrdenes()).thenReturn(List.of(new Orden(1, 1, 1, null, 100.0, null)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orden/listar"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idOrden").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idCliente").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idSucursal").value(1));
    }

    @Test //prueba unitaria que elimina una orden
    public void testEliminarOrden() throws Exception {
        

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orden/eliminar/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(ordenService, times(1)).eliminarOrden(1);
    }

    @Test // prueba unitaria que actualiza una orden
    public void testActualizarOrden() throws Exception {
        Orden orden = new Orden(1, 1, 1, null, 100.0, null);
        when(ordenService.actualizarOrden(orden, 1)).thenReturn(orden);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/orden/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orden)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idOrden").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCliente").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idSucursal").value(1));

    }
    @Test // prueba unitaria que guarda una orden
    public void testGuardarOrden() throws Exception {
        Orden orden = new Orden(1, 1, 1, null, 100.0, null);
        when(ordenService.guardarOrden(orden)).thenReturn(orden);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orden/guardarOrdenSinDetalle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orden)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idOrden").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCliente").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idSucursal").value(1));
    }

    @Test // parchear una orden
    public void testPatchOrden() throws Exception {
        Orden orden = new Orden(1, 1, 1, null, 100.0, null);
        when(ordenService.patchOrden(1, orden)).thenReturn(orden);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/orden/patch/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orden)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idOrden").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCliente").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idSucursal").value(1));
    }



}
