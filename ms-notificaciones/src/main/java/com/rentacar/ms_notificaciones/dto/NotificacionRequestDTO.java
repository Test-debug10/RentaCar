package com.rentacar.ms_notificaciones.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificacionRequestDTO {

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long reservaId;

    @Email(message = "Debe ser un correo electrónico válido")
    @NotBlank(message = "El email del destinatario es obligatorio")
    private String destinatarioEmail;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    @NotBlank(message = "Debe especificar el canal de envío (ej. EMAIL, SMS)")
    private String canal;
}