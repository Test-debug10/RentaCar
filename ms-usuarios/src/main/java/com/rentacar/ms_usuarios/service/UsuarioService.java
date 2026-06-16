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

    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setRut(dto.getRut());
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        Usuario guardado = usuarioRepository.save(usuario);
        return mapearAResponse(guardado);
    }

    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private UsuarioResponseDTO mapearAResponse(Usuario usuario) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(usuario.getId());
        response.setRut(usuario.getRut());
        response.setNombreCompleto(usuario.getNombreCompleto());
        response.setEmail(usuario.getEmail());
        response.setTelefono(usuario.getTelefono());
        return response;
    }
}