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

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/version/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

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