package com.rentacar.ms_usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_usuarios.model.Usuario;
import com.rentacar.ms_usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario patchUsuario(Long id, Usuario parcial) {
        Usuario existente = findById(id);
        
        if (existente == null) {
            return null;
        }

        if (parcial.getRut() != null) {
            existente.setRut(parcial.getRut());
        }
        if (parcial.getNombreCompleto() != null) {
            existente.setNombreCompleto(parcial.getNombreCompleto());
        }
        if (parcial.getEmail() != null) {
            existente.setEmail(parcial.getEmail());
        }
        if (parcial.getTelefono() != null) {
            existente.setTelefono(parcial.getTelefono());
        }

        return usuarioRepository.save(existente);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }
}