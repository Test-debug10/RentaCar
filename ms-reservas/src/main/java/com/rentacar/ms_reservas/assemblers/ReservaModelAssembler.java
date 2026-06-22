package com.rentacar.ms_reservas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rentacar.ms_reservas.controller.ReservaController;
import com.rentacar.ms_reservas.model.Reserva;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ReservaModelAssembler implements RepresentationModelAssembler<Reserva, EntityModel<Reserva>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Reserva> toModel(Reserva reserva) {
        return EntityModel.of(reserva,
            linkTo(methodOn(ReservaController.class).getReservaById(reserva.getId())).withSelfRel(),
            linkTo(methodOn(ReservaController.class).getAllReservas()).withRel("reservas"),
            linkTo(methodOn(ReservaController.class).updateReserva(reserva.getId(), reserva)).withRel("actualizar"),
            linkTo(methodOn(ReservaController.class).deleteReserva(reserva.getId())).withRel("eliminar"),
            linkTo(methodOn(ReservaController.class).patchReserva(reserva.getId(), reserva)).withRel("actualizar-parcial")
        );
    }
}