package com.rentacar.ms_sucursales.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SucursalRequestDTO {

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "La ciudad es obligatoria")
    private String city;

    @NotBlank(message = "El teléfono de contacto es obligatorio")
    private String telefono;
}