package com.rentacar.ms_usuarios.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String rut;
    private String nombreCompleto;
    private String email;
    private String telefono;
}