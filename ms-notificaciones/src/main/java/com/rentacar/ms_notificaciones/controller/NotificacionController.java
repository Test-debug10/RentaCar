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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Notificaciones", description = "API para la gestión de notificaciones")
@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Operation(summary = "Registrar una nueva notificacion")
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crearNotificacion(@Valid @RequestBody NotificacionRequestDTO dto) {
        log.info("Procesando envio por {} para: {}", dto.getCanal(), dto.getDestinatarioEmail());
        NotificacionResponseDTO nueva = notificacionService.registrarNotificacion(dto);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listarNotificaciones() {
        return ResponseEntity.ok(notificacionService.obtenerTodasDTO());
    }
}