package com.rentacar.ms_extras.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_extras.dto.ExtraRequestDTO;
import com.rentacar.ms_extras.dto.ExtraResponseDTO;
import com.rentacar.ms_extras.service.ExtraService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/extras")
public class ExtraController {
    
    @Autowired
    private ExtraService extraService;

    @PostMapping
    public ResponseEntity<ExtraResponseDTO> crearExtra(@Valid @RequestBody ExtraRequestDTO dto) {
        log.info("Registrando nuevo accesorio extra en el catalogo: {}", dto.getNombre());
        ExtraResponseDTO nuevoExtra = extraService.registrarExtra(dto);
        log.info("Accesorio registrado con exito, ID: {}", nuevoExtra.getId());
        return new ResponseEntity<>(nuevoExtra, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExtraResponseDTO>> listarExtras() {
        return ResponseEntity.ok(extraService.obtenerTodos());
    }
}