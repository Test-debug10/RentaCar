package com.rentacar.ms_usuarios.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_usuarios.dto.UsuarioRequestDTO;
import com.rentacar.ms_usuarios.dto.UsuarioResponseDTO;
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

    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto) {
        Usuario u = new Usuario();
        u.setRut(dto.getRut());
        u.setNombreCompleto(dto.getNombreCompleto());
        u.setEmail(dto.getEmail());
        u.setTelefono(dto.getTelefono());
        
        Usuario guardado = save(u);
        return mapToDTO(guardado);
    }

    public List<UsuarioResponseDTO> obtenerTodosDTO() {
        return obtenerTodos().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private UsuarioResponseDTO mapToDTO(Usuario u) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(u.getId());
        dto.setRut(u.getRut());
        dto.setNombreCompleto(u.getNombreCompleto());
        dto.setEmail(u.getEmail());
        dto.setTelefono(u.getTelefono());
        return dto;
    }
}