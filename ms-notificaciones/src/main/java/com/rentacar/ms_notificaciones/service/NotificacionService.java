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

    public NotificacionResponseDTO registrarNotificacion(NotificacionRequestDTO dto) {
        Notificacion notificacion = new Notificacion();
        notificacion.setReservaId(dto.getReservaId());
        notificacion.setDestinatarioEmail(dto.getDestinatarioEmail());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setCanal(dto.getCanal());
        notificacion.setFechaEnvio(LocalDateTime.now());

        // Aquí iría la lógica real de envío de emails (ej. con JavaMailSender).
        // Por ahora, solo registramos el historial en la BD.
        
        Notificacion guardada = notificacionRepository.save(notificacion);
        return mapearAResponse(guardada);
    }

    public List<NotificacionResponseDTO> obtenerTodas() {
        return notificacionRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private NotificacionResponseDTO mapearAResponse(Notificacion notificacion) {
        NotificacionResponseDTO response = new NotificacionResponseDTO();
        response.setId(notificacion.getId());
        response.setReservaId(notificacion.getReservaId());
        response.setDestinatarioEmail(notificacion.getDestinatarioEmail());
        response.setMensaje(notificacion.getMensaje());
        response.setCanal(notificacion.getCanal());
        response.setFechaEnvio(notificacion.getFechaEnvio());
        return response;
    }
}