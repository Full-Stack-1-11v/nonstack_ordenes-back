package com.perfulandia.cl.microservicio_orden.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.service.OrdenService;
import com.perfulandia.cl.microservicio_orden.dto.OrdenRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrdenControllerV2.class)
public class OrdenControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenService ordenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerOrden() throws Exception {
        Orden orden = new Orden(1, 1, 1, null, 100.0, null);
        when(ordenService.obtenerOrden(1)).thenReturn(orden);

        mockMvc.perform(get("/api/v2/orden/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOrden").value(1))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.ordenes.href").exists());
    }

    @Test
    void testObtenerOrdenNoEncontrada() throws Exception {
        when(ordenService.obtenerOrden(99)).thenReturn(null);

        mockMvc.perform(get("/api/v2/orden/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testObtenerOrdenesPorCliente() throws Exception {
        Orden orden = new Orden(1, 1, 1, null, 100.0, null);
        when(ordenService.obtenerOrdenesCliente(1)).thenReturn(List.of(orden));

        mockMvc.perform(get("/api/v2/orden/cliente/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.ordenList[0].idOrden").value(1))
                .andExpect(jsonPath("$._embedded.ordenList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testObtenerOrdenesPorClienteVacio() throws Exception {
        when(ordenService.obtenerOrdenesCliente(2)).thenReturn(List.of());

        mockMvc.perform(get("/api/v2/orden/cliente/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListar() throws Exception {
        Orden orden = new Orden(1, 1, 1, null, 100.0, null);
        when(ordenService.mostrarOrdenes()).thenReturn(List.of(orden));

        mockMvc.perform(get("/api/v2/orden/listar").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.ordenList[0].idOrden").value(1))
                .andExpect(jsonPath("$._embedded.ordenList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testListarVacio() throws Exception {
        when(ordenService.mostrarOrdenes()).thenReturn(List.of());

        mockMvc.perform(get("/api/v2/orden/listar"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarOrden() throws Exception {
        when(ordenService.eliminarOrden(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v2/orden/eliminar/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarOrdenNoEncontrada() throws Exception {
        when(ordenService.eliminarOrden(99)).thenReturn(false);

        mockMvc.perform(delete("/api/v2/orden/eliminar/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarOrden() throws Exception {
        Orden orden = new Orden(1, 1, 1, null, 100.0, null);
        when(ordenService.actualizarOrden(any(Orden.class), eq(1))).thenReturn(orden);

        mockMvc.perform(put("/api/v2/orden/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOrden").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testActualizarOrdenNoEncontrada() throws Exception {
        when(ordenService.actualizarOrden(any(Orden.class), eq(99))).thenReturn(null);

        mockMvc.perform(put("/api/v2/orden/actualizar/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Orden())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearOrden() throws Exception {
        Orden orden = new Orden(1, 1, 1, null, 100.0, null);
        OrdenRequest request = new OrdenRequest(); // Completa los campos necesarios si tu DTO los requiere
        when(ordenService.crearOrden(any(OrdenRequest.class))).thenReturn(orden);

        mockMvc.perform(post("/api/v2/orden/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idOrden").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testCrearOrdenErrorValidacion() throws Exception {
        when(ordenService.crearOrden(any(OrdenRequest.class))).thenThrow(new IllegalArgumentException("Error"));

        mockMvc.perform(post("/api/v2/orden/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new OrdenRequest())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPatchOrden() throws Exception {
        Orden orden = new Orden(1, 1, 1, null, 100.0, null);
        when(ordenService.patchOrden(eq(1), any(Orden.class))).thenReturn(orden);

        mockMvc.perform(patch("/api/v2/orden/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOrden").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testPatchOrdenNoEncontrada() throws Exception {
        when(ordenService.patchOrden(eq(99), any(Orden.class))).thenReturn(null);

        mockMvc.perform(patch("/api/v2/orden/actualizar/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Orden())))
                .andExpect(status().isNotFound());
    }
}