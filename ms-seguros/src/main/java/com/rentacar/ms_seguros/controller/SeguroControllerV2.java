package com.rentacar.ms_seguros.controller;

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

import com.rentacar.ms_seguros.assemblers.SeguroModelAssembler;
import com.rentacar.ms_seguros.model.Seguro;
import com.rentacar.ms_seguros.service.SeguroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Seguros", description = "API para la gestion de seguros")
@RestController
@RequestMapping("/api/v2/seguros")
public class SeguroControllerV2 {

    @Autowired
    private SeguroService seguroService;

    @Autowired
    private SeguroModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Seguro>>> getAllSeguros() {
        List<Seguro> seguros = seguroService.obtenerTodos();
        CollectionModel<EntityModel<Seguro>> collectionModel = assembler.toCollectionModel(seguros);
        
        collectionModel.add(linkTo(methodOn(SeguroControllerV2.class).getAllSeguros()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Seguro>> getSeguroById(@PathVariable Long id) {
        Seguro seguro = seguroService.findById(id);

        if (seguro == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(seguro));
    }

    @Operation(summary = "Registrar un nuevo seguro", description = "Guarda un seguro en la base de datos validando sus atributos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Seguro>> crearSeguro(@Valid @RequestBody Seguro seguro) {
        log.info("Procesando emision de seguro {} para la reserva ID: {}", seguro.getTipoSeguro(), seguro.getReservaId());
        
        Seguro nuevoSeguro = seguroService.save(seguro);
        EntityModel<Seguro> entityModel = assembler.toModel(nuevoSeguro);
        
        log.info("Seguro emitido exitosamente con ID interno: {}", nuevoSeguro.getId());
        
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Seguro>> updateSeguro(@PathVariable Long id, @RequestBody Seguro seguro) {
        seguro.setId(id);
        Seguro updated = seguroService.save(seguro);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Seguro>> patchSeguro(@PathVariable Long id, @RequestBody Seguro seguro) {
        Seguro updated = seguroService.patchSeguro(id, seguro);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSeguro(@PathVariable Long id) {
        Seguro existing = seguroService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        seguroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}