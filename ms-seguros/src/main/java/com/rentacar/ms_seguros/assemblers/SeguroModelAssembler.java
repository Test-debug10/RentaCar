package com.rentacar.ms_seguros.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.rentacar.ms_seguros.controller.SeguroControllerV2;
import com.rentacar.ms_seguros.model.Seguro;

@Component
public class SeguroModelAssembler implements RepresentationModelAssembler<Seguro, EntityModel<Seguro>> {

    @Override
    public EntityModel<Seguro> toModel(Seguro seguro) {
        return EntityModel.of(seguro,
            linkTo(methodOn(SeguroControllerV2.class).getSeguroById(seguro.getId())).withSelfRel(),
            linkTo(methodOn(SeguroControllerV2.class).getAllSeguros()).withRel("seguros"),
            linkTo(methodOn(SeguroControllerV2.class).updateSeguro(seguro.getId(), seguro)).withRel("actualizar"),
            linkTo(methodOn(SeguroControllerV2.class).deleteSeguro(seguro.getId())).withRel("eliminar"),
            linkTo(methodOn(SeguroControllerV2.class).patchSeguro(seguro.getId(), seguro)).withRel("actualizar-parcial")
        );
    }
}