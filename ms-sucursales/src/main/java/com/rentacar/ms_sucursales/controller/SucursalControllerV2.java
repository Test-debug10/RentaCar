package com.rentacar.ms_sucursales.controller;

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

import com.rentacar.ms_sucursales.assemblers.SucursalModelAssembler;
import com.rentacar.ms_sucursales.model.Sucursal;
import com.rentacar.ms_sucursales.service.SucursalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Sucursales", description = "API para la gestion de sucursales")
@RestController
@RequestMapping("/api/v2/sucursales")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> getAllSucursales() {
        log.info("Solicitando listado completo de sucursales operativas");
        List<Sucursal> sucursales = sucursalService.obtenerTodas();
        CollectionModel<EntityModel<Sucursal>> collectionModel = assembler.toCollectionModel(sucursales);
        
        collectionModel.add(linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> getSucursalById(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.findById(id);

        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(sucursal));
    }

    @Operation(summary = "Registrar una nueva sucursal", description = "Guarda los datos de la sucursal en la base de datos validando sus atributos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> crearSucursal(@Valid @RequestBody Sucursal sucursal) {
        log.info("Registrando nueva sucursal: {} en la ciudad de {}", sucursal.getNombre(), sucursal.getCiudad());
        
        Sucursal nueva = sucursalService.save(sucursal);
        EntityModel<Sucursal> entityModel = assembler.toModel(nueva);
        
        log.info("Sucursal registrada exitosamente con ID: {}", nueva.getId());
        
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> updateSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        sucursal.setId(id);
        Sucursal updated = sucursalService.save(sucursal);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> patchSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal updated = sucursalService.patchSucursal(id, sucursal);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSucursal(@PathVariable Long id) {
        Sucursal existing = sucursalService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        sucursalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}