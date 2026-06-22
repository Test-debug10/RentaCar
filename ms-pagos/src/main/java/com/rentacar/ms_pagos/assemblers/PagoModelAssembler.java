package com.rentacar.ms_pagos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rentacar.ms_pagos.controller.PagoController;
import com.rentacar.ms_pagos.model.Pago;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
            linkTo(methodOn(PagoController.class).getPagoById(pago.getId())).withSelfRel(),
            linkTo(methodOn(PagoController.class).getAllPagos()).withRel("pagos"),
            linkTo(methodOn(PagoController.class).updatePago(pago.getId(), pago)).withRel("actualizar"),
            linkTo(methodOn(PagoController.class).deletePago(pago.getId())).withRel("eliminar"),
            linkTo(methodOn(PagoController.class).patchPago(pago.getId(), pago)).withRel("actualizar-parcial")
        );
    }
}