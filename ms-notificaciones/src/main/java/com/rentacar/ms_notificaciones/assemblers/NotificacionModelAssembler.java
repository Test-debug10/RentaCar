package com.rentacar.ms_notificaciones.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.rentacar.ms_notificaciones.controller.NotificacionControllerV2;
import com.rentacar.ms_notificaciones.model.Notificacion;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<Notificacion, EntityModel<Notificacion>> {

    @Override
    public EntityModel<Notificacion> toModel(Notificacion notificacion) {

        return EntityModel.of(notificacion,
            linkTo(methodOn(NotificacionControllerV2.class).getNotificacionById(notificacion.getId())).withSelfRel(),
            linkTo(methodOn(NotificacionControllerV2.class).getAllNotificaciones()).withRel("notificaciones"),
            linkTo(methodOn(NotificacionControllerV2.class).updateNotificacion(notificacion.getId(), notificacion)).withRel("actualizar"),
            linkTo(methodOn(NotificacionControllerV2.class).deleteNotificacion(notificacion.getId())).withRel("eliminar"),
            linkTo(methodOn(NotificacionControllerV2.class).patchNotificacion(notificacion.getId(), notificacion)).withRel("actualizar-parcial")
        );
    }
}