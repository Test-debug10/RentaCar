package com.rentacar.ms_extras.dto;

import lombok.Data;

@Data
public class ExtraResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precioDiario;
    private Integer stockTotal;
}