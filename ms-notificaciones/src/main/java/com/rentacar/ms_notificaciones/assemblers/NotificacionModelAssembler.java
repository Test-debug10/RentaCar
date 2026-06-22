package com.rentacar.ms_notificaciones.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rentacar.ms_notificaciones.controller.NotificacionController;
import com.rentacar.ms_notificaciones.model.Notificacion;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<Notificacion, EntityModel<Notificacion>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Notificacion> toModel(Notificacion notificacion) {

        return EntityModel.of(notificacion,
            linkTo(methodOn(NotificacionController.class).getNotificacionById(notificacion.getId())).withSelfRel(),
            linkTo(methodOn(NotificacionController.class).getAllNotificaciones()).withRel("notificaciones"),
            linkTo(methodOn(NotificacionController.class).updateNotificacion(notificacion.getId(), notificacion)).withRel("actualizar"),
            linkTo(methodOn(NotificacionController.class).deleteNotificacion(notificacion.getId())).withRel("eliminar"),
            linkTo(methodOn(NotificacionController.class).patchNotificacion(notificacion.getId(), notificacion)).withRel("actualizar-parcial")
        );
    }
}