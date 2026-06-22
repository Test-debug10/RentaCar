package com.rentacar.ms_extras.controller;

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

import com.rentacar.ms_extras.assemblers.ExtraModelAssembler;
import com.rentacar.ms_extras.model.Extra;
import com.rentacar.ms_extras.service.ExtraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Extras", description = "API para la gestion de extras")
@RestController
@RequestMapping("/api/v2/extras")
public class ExtraControllerV2 {
    
    @Autowired
    private ExtraService extraService;

    @Autowired
    private ExtraModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Extra>>> getAllExtras() {
        List<Extra> extras = extraService.obtenerTodos();
        CollectionModel<EntityModel<Extra>> collectionModel = assembler.toCollectionModel(extras);
        collectionModel.add(linkTo(methodOn(ExtraControllerV2.class).getAllExtras()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Extra>> getExtraById(@PathVariable Long id) {
        Extra extra = extraService.findById(id);
        
        if (extra == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(assembler.toModel(extra));
    }

    @Operation(summary = "Registrar un nuevo extra", description = "Guarda un extra en la base de datos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Extra>> createExtra(@Valid @RequestBody Extra extra) {
        log.info("Registrando nuevo accesorio extra en el catalogo: {}", extra.getNombre());
        
        Extra newExtra = extraService.save(extra);
        EntityModel<Extra> entityModel = assembler.toModel(newExtra);
        
        log.info("Accesorio registrado con exito, ID: {}", newExtra.getId());
        
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Extra>> updateExtra(@PathVariable Long id, @RequestBody Extra extra) {
        extra.setId(id);
        Extra updated = extraService.save(extra);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Extra>> patchExtra(@PathVariable Long id, @RequestBody Extra extra) {
        Extra updated = extraService.patchExtra(id, extra);
        
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteExtra(@PathVariable Long id) {
        Extra existing = extraService.findById(id);
        
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        
        extraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}