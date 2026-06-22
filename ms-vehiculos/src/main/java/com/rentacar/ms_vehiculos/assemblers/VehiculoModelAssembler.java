package com.rentacar.ms_vehiculos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rentacar.ms_vehiculos.controller.VehiculoController;
import com.rentacar.ms_vehiculos.model.Vehiculo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class VehiculoModelAssembler implements RepresentationModelAssembler<Vehiculo, EntityModel<Vehiculo>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Vehiculo> toModel(Vehiculo vehiculo) {

        return EntityModel.of(vehiculo,
            linkTo(methodOn(VehiculoController.class).getVehiculoById(vehiculo.getId())).withSelfRel(),
            linkTo(methodOn(VehiculoController.class).getAllVehiculos()).withRel("vehiculos"),
            linkTo(methodOn(VehiculoController.class).updateVehiculo(vehiculo.getId(), vehiculo)).withRel("actualizar"),
            linkTo(methodOn(VehiculoController.class).deleteVehiculo(vehiculo.getId())).withRel("eliminar"),
            linkTo(methodOn(VehiculoController.class).patchVehiculo(vehiculo.getId(), vehiculo)).withRel("actualizar-parcial")
        );
    }
}