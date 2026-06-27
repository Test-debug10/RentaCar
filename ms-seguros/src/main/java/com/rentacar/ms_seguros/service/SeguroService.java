package com.rentacar.ms_seguros.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_seguros.client.ReservaClient;
import com.rentacar.ms_seguros.dto.SeguroRequestDTO;
import com.rentacar.ms_seguros.dto.SeguroResponseDTO;
import com.rentacar.ms_seguros.model.Seguro;
import com.rentacar.ms_seguros.repository.SeguroRepository;

@Service
public class SeguroService {

    @Autowired 
    private SeguroRepository seguroRepository;
    
    @Autowired
    private ReservaClient reservaClient;

    public Seguro findById(Long id) {
        return seguroRepository.findById(id).orElse(null);
    }

    public List<Seguro> obtenerTodos() {
        return seguroRepository.findAll();
    }

    public Seguro save(Seguro seguro) {
        try {
            reservaClient.obtenerReservaPorId(seguro.getReservaId());
        } catch (Exception e) {
            throw new IllegalArgumentException("La reserva indicada no existe en el sistema.");
        }
        return seguroRepository.save(seguro);
    }

    public Seguro patchSeguro(Long id, Seguro parcial) {
        Seguro existente = findById(id);
        if (existente == null) {
            return null;
        }

        if (parcial.getReservaId() != null) {
            reservaClient.obtenerReservaPorId(parcial.getReservaId());
            existente.setReservaId(parcial.getReservaId());
        }
        if (parcial.getTipoSeguro() != null) {
            existente.setTipoSeguro(parcial.getTipoSeguro());
        }
        if (parcial.getCoberturaMaxima() != null) {
            existente.setCoberturaMaxima(parcial.getCoberturaMaxima());
        }
        if (parcial.getCostoAdicional() != null) {
            existente.setCostoAdicional(parcial.getCostoAdicional());
        }

        return seguroRepository.save(existente);
    }

    public void deleteById(Long id) {
        seguroRepository.deleteById(id);
    }

    public SeguroResponseDTO registrarSeguro(SeguroRequestDTO dto) {
        Seguro s = new Seguro();
        s.setReservaId(dto.getReservaId());
        s.setTipoSeguro(dto.getTipoSeguro());
        s.setCoberturaMaxima(dto.getCoberturaMaxima());
        s.setCostoAdicional(dto.getCostoAdicional());
        
        Seguro guardado = save(s);
        return mapToDTO(guardado);
    }

    public List<SeguroResponseDTO> obtenerTodosDTO() {
        return obtenerTodos().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private SeguroResponseDTO mapToDTO(Seguro s) {
        SeguroResponseDTO dto = new SeguroResponseDTO();
        dto.setId(s.getId());
        dto.setReservaId(s.getReservaId());
        dto.setTipoSeguro(s.getTipoSeguro());
        dto.setCoberturaMaxima(s.getCoberturaMaxima());
        dto.setCostoAdicional(s.getCostoAdicional());
        return dto;
    }
}