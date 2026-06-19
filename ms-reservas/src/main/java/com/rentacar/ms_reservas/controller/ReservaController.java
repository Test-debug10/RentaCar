package com.rentacar.ms_reservas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_reservas.dto.ReservaRequestDTO;
import com.rentacar.ms_reservas.dto.ReservaResponseDTO;
import com.rentacar.ms_reservas.service.ReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Reservas", description = "API para la gestion de reservas")
@RestController
@RequestMapping("/api/v2/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Registrar una nueva reserva", description = "Guarda los datos de una reserva en la base de datos validando sus atributos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validacion en los datos enviados"),
        @ApiResponse(responseCode = "409", description = "Conflicto: La reserva ya existe")
    })
    
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@Valid @RequestBody ReservaRequestDTO dto) {
        log.info("Iniciando proceso de reserva para el usuario ID: {}", dto.getUsuarioId());
        ReservaResponseDTO nuevaReserva = reservaService.crearReserva(dto);
        log.info("Reserva exitosa con ID: {}. Monto total: {}", nuevaReserva.getId(), nuevaReserva.getMontoTotal());
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }
}