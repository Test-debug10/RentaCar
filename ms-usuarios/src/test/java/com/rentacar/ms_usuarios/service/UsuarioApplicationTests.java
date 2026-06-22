package com.rentacar.ms_usuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.rentacar.ms_usuarios.dto.UsuarioRequestDTO;
import com.rentacar.ms_usuarios.dto.UsuarioResponseDTO;
import com.rentacar.ms_usuarios.model.Usuario;
import com.rentacar.ms_usuarios.repository.UsuarioRepository;

@SpringBootTest
public class UsuarioApplicationTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioMock;
    private UsuarioRequestDTO requestMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setRut("12345678-9");
        usuarioMock.setNombreCompleto("Juan Perez");
        usuarioMock.setEmail("juan@rentacar.cl");
        usuarioMock.setTelefono("+56912345678");

        requestMock = new UsuarioRequestDTO();
        requestMock.setRut("12345678-9");
        requestMock.setNombreCompleto("Juan Perez");
        requestMock.setEmail("juan@rentacar.cl");
        requestMock.setTelefono("+56912345678");
    }

    @Test
    void registrarUsuarioTest() {
        // Given (Simulamos el repositorio)
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        // When (Ejecutamos el servicio)
        UsuarioResponseDTO response = usuarioService.registrarUsuario(requestMock);
        
        // Then (Verificamos resultados)
        assertNotNull(response);
        assertEquals("Juan Perez", response.getNombreCompleto());
        assertEquals("juan@rentacar.cl", response.getEmail());
    }
}