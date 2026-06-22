package com.rentacar.ms_seguros.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rentacar.ms_seguros.controller.SeguroController;
import com.rentacar.ms_seguros.model.Seguro;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class SeguroModelAssembler implements RepresentationModelAssembler<Seguro, EntityModel<Seguro>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Seguro> toModel(Seguro seguro) {
        return EntityModel.of(seguro,
            linkTo(methodOn(SeguroController.class).getSeguroById(seguro.getId())).withSelfRel(),
            linkTo(methodOn(SeguroController.class).getAllSeguros()).withRel("seguros"),
            linkTo(methodOn(SeguroController.class).updateSeguro(seguro.getId(), seguro)).withRel("actualizar"),
            linkTo(methodOn(SeguroController.class).deleteSeguro(seguro.getId())).withRel("eliminar"),
            linkTo(methodOn(SeguroController.class).patchSeguro(seguro.getId(), seguro)).withRel("actualizar-parcial")
        );
    }
}