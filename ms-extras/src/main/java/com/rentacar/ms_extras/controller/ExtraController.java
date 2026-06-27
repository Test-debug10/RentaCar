package com.rentacar.ms_extras.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.rentacar.ms_extras.model.Extra;
import com.rentacar.ms_extras.service.ExtraService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Extras", description = "API para la gestion de extras)")
@RestController
@RequestMapping("/api/v1/extras")
public class ExtraController {
    
    @Autowired
    private ExtraService extraService;

    @PostMapping
    public ResponseEntity<ExtraResponseDTO> crearExtra(@Valid @RequestBody ExtraRequestDTO dto) {
        log.info("Registrando nuevo accesorio extra: {}", dto.getNombre());
        
        Extra extra = new Extra();
        extra.setNombre(dto.getNombre());
        extra.setDescripcion(dto.getDescripcion());
        extra.setPrecioDiario(dto.getPrecioDiario());
        extra.setStockTotal(dto.getStockTotal());

        Extra guardado = extraService.save(extra);

        ExtraResponseDTO resp = new ExtraResponseDTO();
        resp.setId(guardado.getId());
        resp.setNombre(guardado.getNombre());
        resp.setDescripcion(guardado.getDescripcion());
        resp.setPrecioDiario(guardado.getPrecioDiario());
        resp.setStockTotal(guardado.getStockTotal());

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExtraResponseDTO>> listarExtras() {
        List<ExtraResponseDTO> lista = extraService.obtenerTodos().stream().map(e -> {
            ExtraResponseDTO dto = new ExtraResponseDTO();
            dto.setId(e.getId());
            dto.setNombre(e.getNombre());
            dto.setDescripcion(e.getDescripcion());
            dto.setPrecioDiario(e.getPrecioDiario());
            dto.setStockTotal(e.getStockTotal());
            return dto;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(lista);
    }
}