package com.rentacar.ms_usuarios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rentacar.ms_usuarios.controller.UsuarioController;
import com.rentacar.ms_usuarios.model.Usuario;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {

        return EntityModel.of(usuario,
            linkTo(methodOn(UsuarioController.class).getUsuarioById(usuario.getId())).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios"),
            linkTo(methodOn(UsuarioController.class).updateUsuario(usuario.getId(), usuario)).withRel("actualizar"),
            linkTo(methodOn(UsuarioController.class).deleteUsuario(usuario.getId())).withRel("eliminar"),
            linkTo(methodOn(UsuarioController.class).patchUsuario(usuario.getId(), usuario)).withRel("actualizar-parcial")
        );
    }
}