package com.rentacar.ms_notificaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_notificaciones.dto.NotificacionRequestDTO;
import com.rentacar.ms_notificaciones.dto.NotificacionResponseDTO;
import com.rentacar.ms_notificaciones.service.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Notificaciones", description = "API para la gestion de vehiculos")
@RestController
@RequestMapping("/api/v2/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Operation(summary = "Registrar una nueva notificacion", description = "Guarda la notificacion en la base de datos validando sus atributos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificacion creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validacion en los datos enviados"),
        @ApiResponse(responseCode = "409", description = "Conflicto: La notificacion ya existe")
    })
    
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crearNotificacion(@Valid @RequestBody NotificacionRequestDTO dto) {
        log.info("Procesando envio de notificacion por {} para el correo: {}", dto.getCanal(), dto.getDestinatarioEmail());
        NotificacionResponseDTO nuevaNotificacion = notificacionService.registrarNotificacion(dto);
        log.info("Notificacion registrada en el historial con ID: {}", nuevaNotificacion.getId());
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listarNotificaciones() {
        return ResponseEntity.ok(notificacionService.obtenerTodas());
    }
}