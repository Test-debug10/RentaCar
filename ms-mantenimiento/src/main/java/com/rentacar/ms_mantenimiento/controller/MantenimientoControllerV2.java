package com.rentacar.ms_mantenimiento.controller;

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

import com.rentacar.ms_mantenimiento.assemblers.MantenimientoModelAssembler;
import com.rentacar.ms_mantenimiento.model.Mantenimiento;
import com.rentacar.ms_mantenimiento.service.MantenimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Mantenimientos", description = "API para la gestion de mantenimientos")
@RestController
@RequestMapping("/api/v2/mantenimientos")
public class MantenimientoControllerV2 {

    @Autowired
    private MantenimientoService mantenimientoService;

    @Autowired
    private MantenimientoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Mantenimiento>>> getAllMantenimientos() {
        List<Mantenimiento> mantenimientos = mantenimientoService.obtenerTodos();
        CollectionModel<EntityModel<Mantenimiento>> collectionModel = assembler.toCollectionModel(mantenimientos);
        
        collectionModel.add(linkTo(methodOn(MantenimientoControllerV2.class).getAllMantenimientos()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mantenimiento>> getMantenimientoById(@PathVariable Long id) {
        Mantenimiento mantenimiento = mantenimientoService.findById(id);

        if (mantenimiento == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(mantenimiento));
    }

    @Operation(summary = "Registrar un nuevo mantenimiento", description = "Guarda un mantenimiento en la base de datos validando sus atributos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mantenimiento>> crearMantenimiento(@Valid @RequestBody Mantenimiento mantenimiento) {
        log.info("Iniciando registro de mantenimiento {} para el vehiculo ID: {}", mantenimiento.getTipo(), mantenimiento.getVehiculoId());
        
        Mantenimiento nuevoMantenimiento = mantenimientoService.save(mantenimiento);
        EntityModel<Mantenimiento> entityModel = assembler.toModel(nuevoMantenimiento);
        
        log.info("Mantenimiento registrado con ID: {}", nuevoMantenimiento.getId());
        
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mantenimiento>> updateMantenimiento(@PathVariable Long id, @RequestBody Mantenimiento mantenimiento) {
        
        mantenimiento.setId(id);
        Mantenimiento updated = mantenimientoService.save(mantenimiento);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mantenimiento>> patchMantenimiento(@PathVariable Long id, @RequestBody Mantenimiento mantenimiento) {
        Mantenimiento updated = mantenimientoService.patchMantenimiento(id, mantenimiento);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMantenimiento(@PathVariable Long id) {
        Mantenimiento existing = mantenimientoService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        mantenimientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}