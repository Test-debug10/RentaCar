package com.rentacar.ms_evaluaciones.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo; // <-- Corregido a V2
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.rentacar.ms_evaluaciones.controller.EvaluacionControllerV2;
import com.rentacar.ms_evaluaciones.model.Evaluacion;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Evaluacion> toModel(Evaluacion evaluacion) {
        return EntityModel.of(evaluacion,
            linkTo(methodOn(EvaluacionControllerV2.class).getEvaluacionById(evaluacion.getId())).withSelfRel(),
            linkTo(methodOn(EvaluacionControllerV2.class).getAllEvaluaciones()).withRel("evaluaciones"),
            linkTo(methodOn(EvaluacionControllerV2.class).updateEvaluacion(evaluacion.getId(), evaluacion)).withRel("actualizar"),
            linkTo(methodOn(EvaluacionControllerV2.class).deleteEvaluacion(evaluacion.getId())).withRel("eliminar"),
            linkTo(methodOn(EvaluacionControllerV2.class).patchEvaluacion(evaluacion.getId(), evaluacion)).withRel("actualizar-parcial")
        );
    }
}