package com.rentacar.ms_usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_usuarios.dto.UsuarioRequestDTO;
import com.rentacar.ms_usuarios.dto.UsuarioResponseDTO;
import com.rentacar.ms_usuarios.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/version/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("Iniciando registro de nuevo usuario con RUT: {}", dto.getRut());
        UsuarioResponseDTO nuevoUsuario = usuarioService.registrarUsuario(dto);
        log.info("Usuario registrado exitosamente con ID: {}", nuevoUsuario.getId());
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        log.info("Obteniendo la lista de todos los usuarios registrados");
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }
}