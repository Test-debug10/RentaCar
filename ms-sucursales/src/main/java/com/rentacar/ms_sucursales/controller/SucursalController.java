package com.rentacar.ms_sucursales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_sucursales.dto.SucursalRequestDTO;
import com.rentacar.ms_sucursales.dto.SucursalResponseDTO;
import com.rentacar.ms_sucursales.service.SucursalService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Sucursales V1", description = "API para la gestión de sucursales")
@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @PostMapping
    public ResponseEntity<SucursalResponseDTO> crearSucursal(@Valid @RequestBody SucursalRequestDTO dto) {
        log.info("Registrando nueva sucursal: {} en la ciudad de {}", dto.getNombre(), dto.getCity());
        return new ResponseEntity<>(sucursalService.registrarSucursal(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SucursalResponseDTO>> listarSucursales() {
        return ResponseEntity.ok(sucursalService.obtenerTodasDTO());
    }
}