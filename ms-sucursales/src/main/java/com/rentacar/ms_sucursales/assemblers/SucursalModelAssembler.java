package com.rentacar.ms_sucursales.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rentacar.ms_sucursales.controller.SucursalController;
import com.rentacar.ms_sucursales.model.Sucursal;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
            linkTo(methodOn(SucursalController.class).getSucursalById(sucursal.getId())).withSelfRel(),
            linkTo(methodOn(SucursalController.class).getAllSucursales()).withRel("sucursales"),
            linkTo(methodOn(SucursalController.class).updateSucursal(sucursal.getId(), sucursal)).withRel("actualizar"),
            linkTo(methodOn(SucursalController.class).deleteSucursal(sucursal.getId())).withRel("eliminar"),
            linkTo(methodOn(SucursalController.class).patchSucursal(sucursal.getId(), sucursal)).withRel("actualizar-parcial")
        );
    }
}