package com.rentacar.ms_seguros.dto;

import lombok.Data;

@Data
public class SeguroResponseDTO {
    private Long id;
    private Long reservaId;
    private String tipoSeguro;
    private Double coberturaMaxima;
    private Double costoAdicional;
}