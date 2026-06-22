package com.rentacar.ms_mantenimiento.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rentacar.ms_mantenimiento.controller.MantenimientoController;
import com.rentacar.ms_mantenimiento.model.Mantenimiento;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MantenimientoModelAssembler implements RepresentationModelAssembler<Mantenimiento, EntityModel<Mantenimiento>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Mantenimiento> toModel(Mantenimiento mantenimiento) {
        return EntityModel.of(mantenimiento,
            linkTo(methodOn(MantenimientoController.class).getMantenimientoById(mantenimiento.getId())).withSelfRel(),
            linkTo(methodOn(MantenimientoController.class).getAllMantenimientos()).withRel("mantenimientos"),
            linkTo(methodOn(MantenimientoController.class).updateMantenimiento(mantenimiento.getId(), mantenimiento)).withRel("actualizar"),
            linkTo(methodOn(MantenimientoController.class).deleteMantenimiento(mantenimiento.getId())).withRel("eliminar"),
            linkTo(methodOn(MantenimientoController.class).patchMantenimiento(mantenimiento.getId(), mantenimiento)).withRel("actualizar-parcial")
        );
    }
}