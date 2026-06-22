package com.rentacar.ms_vehiculos.controller;

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

import com.rentacar.ms_vehiculos.assemblers.VehiculoModelAssembler;
import com.rentacar.ms_vehiculos.model.Vehiculo;
import com.rentacar.ms_vehiculos.service.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Vehiculos", description = "API para la gestion de vehiculos")
@RestController
@RequestMapping("/api/v2/vehiculos")
public class VehiculoControllerV2 {

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private VehiculoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Vehiculo>>> getAllVehiculos() {
        List<Vehiculo> vehiculos = vehiculoService.obtenerTodos();
        CollectionModel<EntityModel<Vehiculo>> collectionModel = assembler.toCollectionModel(vehiculos);
        
        collectionModel.add(linkTo(methodOn(VehiculoControllerV2.class).getAllVehiculos()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Vehiculo>> getVehiculoById(@PathVariable Long id) {
        Vehiculo vehiculo = vehiculoService.findById(id);

        if (vehiculo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(vehiculo));
    }

    @Operation(summary = "Registrar un nuevo vehiculo", description = "Guarda un vehiculo en la base de datos validando sus atributos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Vehiculo>> crearVehiculo(@Valid @RequestBody Vehiculo vehiculo) {
        Vehiculo nuevoVehiculo = vehiculoService.save(vehiculo);
        EntityModel<Vehiculo> entityModel = assembler.toModel(nuevoVehiculo);
        
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Vehiculo>> updateVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        vehiculo.setId(id);
        Vehiculo updated = vehiculoService.save(vehiculo);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Vehiculo>> patchVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        Vehiculo updated = vehiculoService.patchVehiculo(id, vehiculo);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteVehiculo(@PathVariable Long id) {
        Vehiculo existing = vehiculoService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        vehiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}