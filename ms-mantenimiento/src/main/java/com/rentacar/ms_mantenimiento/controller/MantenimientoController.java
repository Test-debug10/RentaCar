package com.rentacar.ms_mantenimiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_mantenimiento.dto.MantenimientoRequestDTO;
import com.rentacar.ms_mantenimiento.dto.MantenimientoResponseDTO;
import com.rentacar.ms_mantenimiento.service.MantenimientoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/mantenimientos")
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @PostMapping
    public ResponseEntity<MantenimientoResponseDTO> crearMantenimiento(@Valid @RequestBody MantenimientoRequestDTO dto) {
        log.info("Iniciando registro de mantenimiento {} para el vehiculo ID: {}", dto.getTipo(), dto.getVehiculoId());
        MantenimientoResponseDTO nuevoMantenimiento = mantenimientoService.registrarMantenimiento(dto);
        log.info("Mantenimiento registrado con ID: {}", nuevoMantenimiento.getId());
        return new ResponseEntity<>(nuevoMantenimiento, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MantenimientoResponseDTO>> listarMantenimientos() {
        return ResponseEntity.ok(mantenimientoService.obtenerTodos());
    }
}