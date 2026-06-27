package com.rentacar.ms_mantenimiento.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.rentacar.ms_mantenimiento.controller.MantenimientoControllerV2;
import com.rentacar.ms_mantenimiento.model.Mantenimiento;

@Component
public class MantenimientoModelAssembler implements RepresentationModelAssembler<Mantenimiento, EntityModel<Mantenimiento>> {

    @Override
    public EntityModel<Mantenimiento> toModel(Mantenimiento mantenimiento) {
        return EntityModel.of(mantenimiento,
            linkTo(methodOn(MantenimientoControllerV2.class).getMantenimientoById(mantenimiento.getId())).withSelfRel(),
            linkTo(methodOn(MantenimientoControllerV2.class).getAllMantenimientos()).withRel("mantenimientos"),
            linkTo(methodOn(MantenimientoControllerV2.class).updateMantenimiento(mantenimiento.getId(), mantenimiento)).withRel("actualizar"),
            linkTo(methodOn(MantenimientoControllerV2.class).deleteMantenimiento(mantenimiento.getId())).withRel("eliminar"),
            linkTo(methodOn(MantenimientoControllerV2.class).patchMantenimiento(mantenimiento.getId(), mantenimiento)).withRel("actualizar-parcial")
        );
    }
}