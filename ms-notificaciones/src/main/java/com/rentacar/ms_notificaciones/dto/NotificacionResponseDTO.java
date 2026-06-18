package com.rentacar.ms_notificaciones.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificacionResponseDTO {
    private Long id;
    private Long reservaId;
    private String destinatarioEmail;
    private String mensaje;
    private String canal;
    private LocalDateTime fechaEnvio;
}