package com.rentacar.ms_reservas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.rentacar.ms_reservas.controller.ReservaControllerV2;
import com.rentacar.ms_reservas.model.Reserva;

@Component
public class ReservaModelAssembler implements RepresentationModelAssembler<Reserva, EntityModel<Reserva>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Reserva> toModel(Reserva reserva) {
        return EntityModel.of(reserva,
            linkTo(methodOn(ReservaControllerV2.class).getReservaById(reserva.getId())).withSelfRel(),
            linkTo(methodOn(ReservaControllerV2.class).getAllReservas()).withRel("reservas"),
            linkTo(methodOn(ReservaControllerV2.class).updateReserva(reserva.getId(), reserva)).withRel("actualizar"),
            linkTo(methodOn(ReservaControllerV2.class).deleteReserva(reserva.getId())).withRel("eliminar"),
            linkTo(methodOn(ReservaControllerV2.class).patchReserva(reserva.getId(), reserva)).withRel("actualizar-parcial")
        );
    }
}