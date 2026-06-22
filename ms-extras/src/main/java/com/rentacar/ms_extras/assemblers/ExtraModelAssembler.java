package com.rentacar.ms_extras.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component; // <-- Importación de la V2

import com.rentacar.ms_extras.controller.ExtraControllerV2;
import com.rentacar.ms_extras.model.Extra;

@Component
public class ExtraModelAssembler implements RepresentationModelAssembler<Extra, EntityModel<Extra>> {

    @Override
    public EntityModel<Extra> toModel(Extra extra) {
        return EntityModel.of(extra,
            linkTo(methodOn(ExtraControllerV2.class).getExtraById(extra.getId())).withSelfRel(),
            linkTo(methodOn(ExtraControllerV2.class).getAllExtras()).withRel("extras"));
    }
}