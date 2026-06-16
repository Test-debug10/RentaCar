package com.rentacar.ms_extras.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_extras.dto.ExtraRequestDTO;
import com.rentacar.ms_extras.dto.ExtraResponseDTO;
import com.rentacar.ms_extras.model.Extra;
import com.rentacar.ms_extras.repository.ExtraRepository;

@Service
public class ExtraService {

    @Autowired
    private ExtraRepository extraRepository;

    public ExtraResponseDTO registrarExtra(ExtraRequestDTO dto) {
        Extra extra = new Extra();
        extra.setNombre(dto.getNombre());
        extra.setDescripcion(dto.getDescripcion());
        extra.setPrecioDiario(dto.getPrecioDiario());
        extra.setStockTotal(dto.getStockTotal());

        Extra guardado = extraRepository.save(extra);
        return mapearAResponse(guardado);
    }

    public List<ExtraResponseDTO> obtenerTodos() {
        return extraRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private ExtraResponseDTO mapearAResponse(Extra extra) {
        ExtraResponseDTO response = new ExtraResponseDTO();
        response.setId(extra.getId());
        response.setNombre(extra.getNombre());
        response.setDescripcion(extra.getDescripcion());
        response.setPrecioDiario(extra.getPrecioDiario());
        response.setStockTotal(extra.getStockTotal());
        return response;
    }
}