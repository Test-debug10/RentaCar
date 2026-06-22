package com.rentacar.ms_usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_usuarios.assemblers.UsuarioModelAssembler;
import com.rentacar.ms_usuarios.model.Usuario;
import com.rentacar.ms_usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Usuarios", description = "API para la gestion de usuarios")
@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getAllUsuarios() {
        log.info("Obteniendo la lista de todos los usuarios registrados");
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        CollectionModel<EntityModel<Usuario>> collectionModel = assembler.toCollectionModel(usuarios);
        
        collectionModel.add(linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @Operation(summary = "Registrar un nuevo usuario", description = "Guarda un usuario en la base de datos validando sus atributos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> crearUsuario(@Valid @RequestBody Usuario usuario) {
        log.info("Iniciando registro de nuevo usuario con RUT: {}", usuario.getRut());
        
        Usuario nuevoUsuario = usuarioService.save(usuario);
        EntityModel<Usuario> entityModel = assembler.toModel(nuevoUsuario);
        
        log.info("Usuario registrado exitosamente con ID: {}", nuevoUsuario.getId());
        
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario updated = usuarioService.save(usuario);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> patchUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario updated = usuarioService.patchUsuario(id, usuario);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        Usuario existing = usuarioService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}