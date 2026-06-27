package com.rentacar.ms_seguros.controller;

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

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Seguros V2", description = "API para la gestión de seguros con hateoas")
@RestController
@RequestMapping("/api/v2/seguros")
public class SeguroControllerV2 {

    @Autowired 
    private SeguroService seguroService;
    
    @Autowired 
    private SeguroModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Seguro>>> getAllSeguros() {
        return ResponseEntity.ok(assembler.toCollectionModel(seguroService.obtenerTodos()).add(linkTo(methodOn(SeguroControllerV2.class).getAllSeguros()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Seguro>> getSeguroById(@PathVariable Long id) {
        Seguro s = seguroService.findById(id);
        return (s == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(s));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Seguro>> crearSeguro(@Valid @RequestBody Seguro seguro) {
        EntityModel<Seguro> model = assembler.toModel(seguroService.save(seguro));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Seguro>> updateSeguro(@PathVariable Long id, @RequestBody Seguro seguro) {
        seguro.setId(id);
        return ResponseEntity.ok(assembler.toModel(seguroService.save(seguro)));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Seguro>> patchSeguro(@PathVariable Long id, @RequestBody Seguro seguro) {
        Seguro updated = seguroService.patchSeguro(id, seguro);
        return (updated == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSeguro(@PathVariable Long id) {
        if (seguroService.findById(id) == null) return ResponseEntity.notFound().build();
        seguroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}