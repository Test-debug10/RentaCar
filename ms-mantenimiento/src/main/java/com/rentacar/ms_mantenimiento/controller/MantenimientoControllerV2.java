package com.rentacar.ms_mantenimiento.controller;

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

import com.rentacar.ms_mantenimiento.assemblers.MantenimientoModelAssembler;
import com.rentacar.ms_mantenimiento.model.Mantenimiento;
import com.rentacar.ms_mantenimiento.service.MantenimientoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Mantenimientos V2", description = "API HATEOAS")
@RestController
@RequestMapping("/api/v2/mantenimientos")
public class MantenimientoControllerV2 {

    @Autowired 
    private MantenimientoService mantenimientoService;
    
    @Autowired 
    private MantenimientoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Mantenimiento>>> getAllMantenimientos() {
        return ResponseEntity.ok(assembler.toCollectionModel(mantenimientoService.obtenerTodos()).add(linkTo(methodOn(MantenimientoControllerV2.class).getAllMantenimientos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mantenimiento>> getMantenimientoById(@PathVariable Long id) {
        Mantenimiento m = mantenimientoService.findById(id);
        return (m == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(m));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mantenimiento>> crearMantenimiento(@Valid @RequestBody Mantenimiento m) {
        EntityModel<Mantenimiento> model = assembler.toModel(mantenimientoService.save(m));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mantenimiento>> updateMantenimiento(@PathVariable Long id, @RequestBody Mantenimiento m) {
        m.setId(id);
        return ResponseEntity.ok(assembler.toModel(mantenimientoService.save(m)));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mantenimiento>> patchMantenimiento(@PathVariable Long id, @RequestBody Mantenimiento m) {
        Mantenimiento updated = mantenimientoService.patchMantenimiento(id, m);
        return (updated == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMantenimiento(@PathVariable Long id) {
        if (mantenimientoService.findById(id) == null) return ResponseEntity.notFound().build();
        mantenimientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}