package com.rentacar.ms_notificaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_notificaciones.assemblers.NotificacionModelAssembler;
import com.rentacar.ms_notificaciones.model.Notificacion;
import com.rentacar.ms_notificaciones.service.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Notificaciones", description = "API para la gestión de notificaciones")
@RestController
@RequestMapping("/api/v2/notificaciones")
public class NotificacionControllerV2 {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Notificacion>>> getAllNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.obtenerTodas();
        CollectionModel<EntityModel<Notificacion>> collectionModel = assembler.toCollectionModel(notificaciones);
        
        collectionModel.add(linkTo(methodOn(NotificacionControllerV2.class).getAllNotificaciones()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Notificacion>> getNotificacionById(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.findById(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(notificacion));
    }

    @Operation(summary = "Registrar una nueva notificacion", description = "Guarda la notificacion en la base de datos validando sus atributos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Notificacion>> createNotificacion(@Valid @RequestBody Notificacion notificacion) {
        log.info("Procesando envio de notificacion por {} para el correo: {}", notificacion.getCanal(), notificacion.getDestinatarioEmail());
        
        Notificacion nueva = notificacionService.save(notificacion);
        EntityModel<Notificacion> entityModel = assembler.toModel(nueva);
        
        log.info("Notificacion registrada en el historial con ID: {}", nueva.getId());
        
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Notificacion>> updateNotificacion(@PathVariable Long id, @RequestBody Notificacion notificacion) {
        
        notificacion.setId(id);
        Notificacion updated = notificacionService.save(notificacion);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Notificacion>> patchNotificacion(@PathVariable Long id, @RequestBody Notificacion notificacion) {
        
        Notificacion updated = notificacionService.patchNotificacion(id, notificacion);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
        Notificacion existing = notificacionService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        notificacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}