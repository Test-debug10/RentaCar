package com.rentacar.ms_evaluaciones.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rentacar.ms_evaluaciones.controller.EvaluacionController;
import com.rentacar.ms_evaluaciones.model.Evaluacion;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Evaluacion> toModel(Evaluacion evaluacion) {
        return EntityModel.of(evaluacion,
            linkTo(methodOn(EvaluacionController.class).getEvaluacionById(evaluacion.getId())).withSelfRel(),
            linkTo(methodOn(EvaluacionController.class).getAllEvaluaciones()).withRel("evaluaciones"),
            linkTo(methodOn(EvaluacionController.class).updateEvaluacion(evaluacion.getId(), evaluacion)).withRel("actualizar"),
            linkTo(methodOn(EvaluacionController.class).deleteEvaluacion(evaluacion.getId())).withRel("eliminar"),
            linkTo(methodOn(EvaluacionController.class).patchEvaluacion(evaluacion.getId(), evaluacion)).withRel("actualizar-parcial")
        );
    }
}