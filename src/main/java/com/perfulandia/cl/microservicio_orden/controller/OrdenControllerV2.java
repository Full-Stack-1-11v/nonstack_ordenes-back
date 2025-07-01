package com.perfulandia.cl.microservicio_orden.controller;

import com.perfulandia.cl.microservicio_orden.model.Orden;
import com.perfulandia.cl.microservicio_orden.service.OrdenService;
import com.perfulandia.cl.microservicio_orden.dto.OrdenRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador REST para la gestión de órdenes con soporte HATEOAS.
 * Provee endpoints para CRUD y consultas de órdenes.
 */
@RestController
@RequestMapping("/api/v2/orden")
public class OrdenControllerV2 {

    private static final Logger logger = LoggerFactory.getLogger(OrdenControllerV2.class);

    @Autowired
    private OrdenService ordenService;

    /**
     * Obtiene una orden por su ID.
     * @param id ID de la orden.
     * @return ResponseEntity con la orden y enlaces HATEOAS, o 404 si no existe.
     */
    @Operation(summary = "Obtiene una orden por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden encontrada",
            content = @Content(schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Orden>> obtenerOrden(@PathVariable int id) {
        logger.info("Buscando orden con id: {}", id);
        Orden orden = ordenService.obtenerOrden(id);
        if (orden == null) {
            logger.warn("Orden no encontrada con id: {}", id);
            return ResponseEntity.notFound().build();
        }
        EntityModel<Orden> resource = EntityModel.of(orden,
                linkTo(methodOn(OrdenControllerV2.class).obtenerOrden(id)).withSelfRel(),
                linkTo(methodOn(OrdenControllerV2.class).listar()).withRel("ordenes"),
                linkTo(methodOn(OrdenControllerV2.class).eliminarOrden(id)).withRel("eliminar"),
                linkTo(methodOn(OrdenControllerV2.class).actualizarOrden(orden, id)).withRel("actualizar")
        );
        logger.info("Orden encontrada y devuelta con id: {}", id);
        return ResponseEntity.ok(resource);
    }

    /**
     * Obtiene todas las órdenes de un cliente.
     * @param idCliente ID del cliente.
     * @return ResponseEntity con la colección de órdenes y enlaces HATEOAS, o 204 si no hay resultados.
     */
    @Operation(summary = "Obtiene todas las órdenes de un cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Órdenes encontradas",
            content = @Content(schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "204", description = "No hay órdenes para el cliente", content = @Content)
    })
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<CollectionModel<EntityModel<Orden>>> obtenerOrdenesPorCliente(@PathVariable int idCliente) {
        logger.info("Listando órdenes para cliente id: {}", idCliente);
        List<Orden> ordenes = ordenService.obtenerOrdenesCliente(idCliente);
        if (ordenes.isEmpty()) {
            logger.info("No se encontraron órdenes para el cliente id: {}", idCliente);
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<Orden>> ordenResources = ordenes.stream()
                .map(orden -> EntityModel.of(orden,
                        linkTo(methodOn(OrdenControllerV2.class).obtenerOrden(orden.getIdOrden())).withSelfRel()
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Orden>> collectionModel = CollectionModel.of(ordenResources,
                linkTo(methodOn(OrdenControllerV2.class).obtenerOrdenesPorCliente(idCliente)).withSelfRel()
        );
        logger.info("Órdenes devueltas para cliente id: {}", idCliente);
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Lista todas las órdenes existentes.
     * @return ResponseEntity con la colección de órdenes y enlaces HATEOAS, o 204 si no hay resultados.
     */
    @Operation(summary = "Lista todas las órdenes existentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Órdenes listadas",
            content = @Content(schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "204", description = "No hay órdenes", content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<CollectionModel<EntityModel<Orden>>> listar() {
        logger.info("Listando todas las órdenes");
        List<Orden> ordenes = ordenService.mostrarOrdenes();
        if (ordenes.isEmpty()) {
            logger.info("No hay órdenes registradas");
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<Orden>> ordenResources = ordenes.stream()
                .map(orden -> EntityModel.of(orden,
                        linkTo(methodOn(OrdenControllerV2.class).obtenerOrden(orden.getIdOrden())).withSelfRel()
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Orden>> collectionModel = CollectionModel.of(ordenResources,
                linkTo(methodOn(OrdenControllerV2.class).listar()).withSelfRel()
        );
        logger.info("Órdenes listadas correctamente");
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Elimina una orden por su ID.
     * @param idOrden ID de la orden a eliminar.
     * @return ResponseEntity con estado 200 si se elimina, 404 si no existe.
     */
    @Operation(summary = "Elimina una orden por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden eliminada", content = @Content),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content)
    })
    @DeleteMapping("/eliminar/{idOrden}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable int idOrden) {
        logger.info("Eliminando orden con id: {}", idOrden);
        boolean eliminado = ordenService.eliminarOrden(idOrden);
        if (!eliminado) {
            logger.warn("No se pudo eliminar, orden no encontrada con id: {}", idOrden);
            return ResponseEntity.notFound().build();
        }
        logger.info("Orden eliminada con id: {}", idOrden);
        return ResponseEntity.ok().build();
    }

    /**
     * Actualiza una orden existente.
     * @param orden Objeto Orden con los datos actualizados.
     * @param idOrden ID de la orden a actualizar.
     * @return ResponseEntity con la orden actualizada y enlaces HATEOAS, o 404 si no existe.
     */
    @Operation(summary = "Actualiza una orden existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden actualizada",
            content = @Content(schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content)
    })
    @PutMapping("/actualizar/{idOrden}")
    public ResponseEntity<EntityModel<Orden>> actualizarOrden(@RequestBody Orden orden, @PathVariable int idOrden) {
        logger.info("Actualizando orden con id: {}", idOrden);
        Orden ordenActualizada = ordenService.actualizarOrden(orden, idOrden);
        if (ordenActualizada == null) {
            logger.warn("No se pudo actualizar, orden no encontrada con id: {}", idOrden);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EntityModel<Orden> resource = EntityModel.of(ordenActualizada,
                linkTo(methodOn(OrdenControllerV2.class).obtenerOrden(idOrden)).withSelfRel(),
                linkTo(methodOn(OrdenControllerV2.class).listar()).withRel("ordenes")
        );
        logger.info("Orden actualizada con id: {}", idOrden);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Crea una nueva orden.
     * @param request DTO con los datos de la orden.
     * @return ResponseEntity con la orden creada y enlaces HATEOAS, o error si falla.
     */
    @Operation(summary = "Crea una nueva orden")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Orden creada",
            content = @Content(schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/guardar")
    public ResponseEntity<?> crearOrden(@Valid @RequestBody OrdenRequest request) {
        logger.info("Creando nueva orden");
        try {
            Orden nuevaOrden = ordenService.crearOrden(request);
            EntityModel<Orden> resource = EntityModel.of(nuevaOrden,
                    linkTo(methodOn(OrdenControllerV2.class).obtenerOrden(nuevaOrden.getIdOrden())).withSelfRel(),
                    linkTo(methodOn(OrdenControllerV2.class).listar()).withRel("ordenes")
            );
            logger.info("Orden creada con id: {}", nuevaOrden.getIdOrden());
            return new ResponseEntity<>(resource, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al crear orden: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            logger.error("Error interno al crear orden: {}", e.getMessage());
            return new ResponseEntity<>("Error al procesar la orden: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza parcialmente una orden existente.
     * @param idOrden ID de la orden a actualizar.
     * @param orden Objeto Orden con los datos a modificar.
     * @return ResponseEntity con la orden actualizada y enlaces HATEOAS, o 404 si no existe.
     */
    @Operation(summary = "Actualiza parcialmente una orden existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden actualizada",
            content = @Content(schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content)
    })
    @PatchMapping("/actualizar/{idOrden}")
    public ResponseEntity<EntityModel<Orden>> patchOrden(@PathVariable int idOrden, @RequestBody Orden orden) {
        logger.info("Actualizando parcialmente orden con id: {}", idOrden);
        Orden ordenActualizada = ordenService.patchOrden(idOrden, orden);
        if (ordenActualizada == null) {
            logger.warn("No se pudo actualizar parcialmente, orden no encontrada con id: {}", idOrden);
            return ResponseEntity.notFound().build();
        }
        EntityModel<Orden> resource = EntityModel.of(ordenActualizada,
                linkTo(methodOn(OrdenControllerV2.class).obtenerOrden(idOrden)).withSelfRel(),
                linkTo(methodOn(OrdenControllerV2.class).listar()).withRel("ordenes")
        );
        logger.info("Orden parcialmente actualizada con id: {}", idOrden);
        return ResponseEntity.ok(resource);
    }
}
