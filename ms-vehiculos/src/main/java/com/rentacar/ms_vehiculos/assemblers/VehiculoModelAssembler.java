package com.rentacar.ms_vehiculos.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.rentacar.ms_vehiculos.controller.VehiculoControllerV2;
import com.rentacar.ms_vehiculos.model.Vehiculo;

@Component
public class VehiculoModelAssembler implements RepresentationModelAssembler<Vehiculo, EntityModel<Vehiculo>> {

    @Override
    public EntityModel<Vehiculo> toModel(Vehiculo vehiculo) {
        return EntityModel.of(vehiculo,
            linkTo(methodOn(VehiculoControllerV2.class).getVehiculoById(vehiculo.getId())).withSelfRel(),
            linkTo(methodOn(VehiculoControllerV2.class).getAllVehiculos()).withRel("vehiculos"),
            linkTo(methodOn(VehiculoControllerV2.class).updateVehiculo(vehiculo.getId(), vehiculo)).withRel("actualizar"),
            linkTo(methodOn(VehiculoControllerV2.class).deleteVehiculo(vehiculo.getId())).withRel("eliminar"),
            linkTo(methodOn(VehiculoControllerV2.class).patchVehiculo(vehiculo.getId(), vehiculo)).withRel("actualizar-parcial")
        );
    }
}