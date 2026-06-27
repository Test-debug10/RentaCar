package com.rentacar.ms_notificaciones.controller;

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

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Notificaciones V2", description = "Hateoas")
@RestController
@RequestMapping("/api/v2/notificaciones")
public class NotificacionControllerV2 {

    @Autowired 
    private NotificacionService notificacionService;
    
    @Autowired 
    private NotificacionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Notificacion>>> getAllNotificaciones() {
        return ResponseEntity.ok(assembler.toCollectionModel(notificacionService.obtenerTodas()).add(linkTo(methodOn(NotificacionControllerV2.class).getAllNotificaciones()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Notificacion>> getNotificacionById(@PathVariable Long id) {
        Notificacion n = notificacionService.findById(id);
        return (n == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(n));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Notificacion>> createNotificacion(@Valid @RequestBody Notificacion n) {
        EntityModel<Notificacion> model = assembler.toModel(notificacionService.save(n));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Notificacion>> updateNotificacion(@PathVariable Long id, @RequestBody Notificacion n) {
        n.setId(id);
        EntityModel<Notificacion> model = assembler.toModel(notificacionService.save(n));
        return ResponseEntity.ok(model);
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Notificacion>> patchNotificacion(@PathVariable Long id, @RequestBody Notificacion n) {
        Notificacion updated = notificacionService.patchNotificacion(id, n);
        return (updated == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
        if (notificacionService.findById(id) == null) 
            return ResponseEntity.notFound().build();
        
        notificacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}