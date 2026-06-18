package com.rentacar.ms_evaluaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_evaluaciones.dto.EvaluacionRequestDTO;
import com.rentacar.ms_evaluaciones.dto.EvaluacionResponseDTO;
import com.rentacar.ms_evaluaciones.service.EvaluacionService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping
    public ResponseEntity<EvaluacionResponseDTO> crearEvaluacion(@Valid @RequestBody EvaluacionRequestDTO dto) {
        log.info("Recibiendo nueva evaluacion para la reserva ID: {}", dto.getReservaId());
        EvaluacionResponseDTO nuevaEvaluacion = evaluacionService.registrarEvaluacion(dto);
        log.info("Evaluacion guardada exitosamente con ID: {}", nuevaEvaluacion.getId());
        return new ResponseEntity<>(nuevaEvaluacion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EvaluacionResponseDTO>> listarEvaluaciones() {
        return ResponseEntity.ok(evaluacionService.obtenerTodas());
    }
}