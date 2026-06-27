package com.rentacar.ms_evaluaciones.controller;

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

import com.rentacar.ms_evaluaciones.assemblers.EvaluacionModelAssembler;
import com.rentacar.ms_evaluaciones.model.Evaluacion;
import com.rentacar.ms_evaluaciones.service.EvaluacionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Evaluaciones", description = "API para la gestión de evaluaciones")
@RestController
@RequestMapping("/api/v2/evaluaciones")
public class EvaluacionControllerV2 {

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private EvaluacionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Evaluacion>>> getAllEvaluaciones() {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerTodas();
        CollectionModel<EntityModel<Evaluacion>> collectionModel = assembler.toCollectionModel(evaluaciones);
        
        collectionModel.add(linkTo(methodOn(EvaluacionControllerV2.class).getAllEvaluaciones()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Evaluacion>> getEvaluacionById(@PathVariable Long id) {
        Evaluacion evaluacion = evaluacionService.findById(id);

        if (evaluacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(evaluacion));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Evaluacion>> createEvaluacion(@RequestBody Evaluacion evaluacion) {
        Evaluacion newEvaluacion = evaluacionService.save(evaluacion);
        EntityModel<Evaluacion> entityModel = assembler.toModel(newEvaluacion);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Evaluacion>> updateEvaluacion(@PathVariable Long id, @RequestBody Evaluacion evaluacion) {
        evaluacion.setId(id);
        Evaluacion updated = evaluacionService.save(evaluacion);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Evaluacion>> patchEvaluacion(@PathVariable Long id, @RequestBody Evaluacion evaluacion) {
        Evaluacion updated = evaluacionService.patchEvaluacion(id, evaluacion);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteEvaluacion(@PathVariable Long id) {
        Evaluacion existing = evaluacionService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        evaluacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}