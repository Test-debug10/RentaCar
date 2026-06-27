package com.rentacar.ms_sucursales.controller;

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

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Sucursales V2", description = "API para gestion de sucursales con HATEOAS")
@RestController
@RequestMapping("/api/v2/sucursales")
public class SucursalControllerV2 {

    @Autowired 
    private SucursalService sucursalService;
    
    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> getAllSucursales() {
        return ResponseEntity.ok(assembler.toCollectionModel(sucursalService.obtenerTodas()).add(linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> getSucursalById(@PathVariable Long id) {
        Sucursal s = sucursalService.findById(id);
        return (s == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(s));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> crearSucursal(@Valid @RequestBody Sucursal sucursal) {
        EntityModel<Sucursal> model = assembler.toModel(sucursalService.save(sucursal));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> updateSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        sucursal.setId(id);
        return ResponseEntity.ok(assembler.toModel(sucursalService.save(sucursal)));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> patchSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal updated = sucursalService.patchSucursal(id, sucursal);
        return (updated == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSucursal(@PathVariable Long id) {
        if (sucursalService.findById(id) == null) return ResponseEntity.notFound().build();
        sucursalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}