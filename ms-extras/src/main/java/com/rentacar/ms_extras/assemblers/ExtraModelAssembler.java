package com.rentacar.ms_extras.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rentacar.ms_extras.controller.ExtraController;
import com.rentacar.ms_extras.model.Extra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ExtraModelAssembler implements RepresentationModelAssembler<Extra, EntityModel<Extra>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Extra> toModel(Extra extra) {

        return EntityModel.of(extra,
            linkTo(methodOn(ExtraController.class).getExtraById(extra.getId())).withSelfRel(),
            linkTo(methodOn(ExtraController.class).getAllExtras()).withRel("extras"),
            linkTo(methodOn(ExtraController.class).updateExtra(extra.getId(), extra)).withRel("actualizar"),
            linkTo(methodOn(ExtraController.class).deleteExtra(extra.getId())).withRel("eliminar"),
            linkTo(methodOn(ExtraController.class).patchExtra(extra.getId(), extra)).withRel("actualizar-parcial")
        );
    }
}