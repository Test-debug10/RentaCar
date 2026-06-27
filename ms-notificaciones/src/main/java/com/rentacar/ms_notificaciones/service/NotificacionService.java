package com.rentacar.ms_notificaciones.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_notificaciones.dto.NotificacionRequestDTO;
import com.rentacar.ms_notificaciones.dto.NotificacionResponseDTO;
import com.rentacar.ms_notificaciones.model.Notificacion;
import com.rentacar.ms_notificaciones.repository.NotificacionRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public Notificacion findById(Long id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    public Notificacion save(Notificacion notificacion) {
        if (notificacion.getFechaEnvio() == null) {
            notificacion.setFechaEnvio(LocalDateTime.now());
        }
        return notificacionRepository.save(notificacion);
    }

    public Notificacion patchNotificacion(Long id, Notificacion parcial) {
        Notificacion existente = findById(id);
        if (existente == null) 
            return null;

        if (parcial.getReservaId() != null) {
            existente.setReservaId(parcial.getReservaId());
        }
        if (parcial.getDestinatarioEmail() != null) {
            existente.setDestinatarioEmail(parcial.getDestinatarioEmail());
        }
        if (parcial.getMensaje() != null) {
            existente.setMensaje(parcial.getMensaje());
        }
        if (parcial.getCanal() != null) {
            existente.setCanal(parcial.getCanal());
        }

        return notificacionRepository.save(existente);
    }

    public void deleteById(Long id) {
        notificacionRepository.deleteById(id);
    }

    public NotificacionResponseDTO registrarNotificacion(NotificacionRequestDTO dto) {
        Notificacion n = new Notificacion();
        n.setReservaId(dto.getReservaId());
        n.setDestinatarioEmail(dto.getDestinatarioEmail());
        n.setMensaje(dto.getMensaje());
        n.setCanal(dto.getCanal());
        
        Notificacion guardada = save(n);
        return mapToDTO(guardada);
    }

    public List<NotificacionResponseDTO> obtenerTodasDTO() {
        return obtenerTodas().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private NotificacionResponseDTO mapToDTO(Notificacion n) {
        NotificacionResponseDTO dto = new NotificacionResponseDTO();
        dto.setId(n.getId());
        dto.setReservaId(n.getReservaId());
        dto.setDestinatarioEmail(n.getDestinatarioEmail());
        dto.setMensaje(n.getMensaje());
        dto.setCanal(n.getCanal());
        dto.setFechaEnvio(n.getFechaEnvio());
        return dto;
    }
}