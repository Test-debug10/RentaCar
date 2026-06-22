package com.rentacar.ms_pagos.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.rentacar.ms_pagos.controller.PagoControllerV2;
import com.rentacar.ms_pagos.model.Pago;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
            linkTo(methodOn(PagoControllerV2.class).getPagoById(pago.getId())).withSelfRel(),
            linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withRel("pagos"),
            linkTo(methodOn(PagoControllerV2.class).updatePago(pago.getId(), pago)).withRel("actualizar"),
            linkTo(methodOn(PagoControllerV2.class).deletePago(pago.getId())).withRel("eliminar"),
            linkTo(methodOn(PagoControllerV2.class).patchPago(pago.getId(), pago)).withRel("actualizar-parcial")
        );
    }
}