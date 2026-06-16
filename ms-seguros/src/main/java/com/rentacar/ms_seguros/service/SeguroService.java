package com.rentacar.ms_seguros.service;

import com.rentacar.ms_seguros.client.ReservaClient;
import com.rentacar.ms-seguros.dto.SeguroRequestDTO;
import com.rentacar.ms_seguros.dto.SeguroResponseDTO;
import com.rentacar.ms_seguros.model.Seguro;
import com.rentacar.ms_seguros.repository.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeguroService {

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private ReservaClient reservaClient;

    public SeguroResponseDTO registrarSeguro(SeguroRequestDTO dto) {
        // Validación de existencia de la reserva en el microservicio correspondiente
        try {
            reservaClient.obtenerReservaPorId(dto.getReservaId());
        } catch (Exception e) {
            throw new IllegalArgumentException("La reserva indicada no existe en el sistema.");
        }

        Seguro seguro = new Seguro();
        seguro.setReservaId(dto.getReservaId());
        seguro.setTipoSeguro(dto.getTipoSeguro());
        seguro.setCoberturaMaxima(dto.getCoberturaMaxima());
        seguro.setCostoAdicional(dto.getCostoAdicional());

        Seguro guardado = seguroRepository.save(seguro);
        return mapearAResponse(guardado);
    }

    public List<SeguroResponseDTO> obtenerTodos() {
        return seguroRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private SeguroResponseDTO mapearAResponse(Seguro seguro) {
        SeguroResponseDTO response = new SeguroResponseDTO();
        response.setId(seguro.getId());
        response.setReservaId(seguro.getReservaId());
        response.setTipoSeguro(seguro.getTipoSeguro());
        response.setCoberturaMaxima(seguro.getCoberturaMaxima());
        response.setCostoAdicional(seguro.getCostoAdicional());
        return response;
    }
}