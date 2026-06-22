package com.rentacar.ms_notificaciones.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_notificaciones.model.Notificacion;
import com.rentacar.ms_notificaciones.repository.NotificacionRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    public Notificacion findById(Long id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    public Notificacion save(Notificacion notificacion) {
        if (notificacion.getFechaEnvio() == null) {
            notificacion.setFechaEnvio(LocalDateTime.now());
        }

        return notificacionRepository.save(notificacion);
    }

    public Notificacion patchNotificacion(Long id, Notificacion parcial) {
        Notificacion existente = findById(id);
        
        if (existente == null) {
            return null;
        }

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
}