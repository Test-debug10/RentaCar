package com.rentacar.ms_sucursales.dto;

import lombok.Data;

@Data
public class SucursalResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;
}