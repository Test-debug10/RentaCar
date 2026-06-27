package com.rentacar.ms_sucursales.service;

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

import com.rentacar.ms_sucursales.dto.SucursalRequestDTO;
import com.rentacar.ms_sucursales.dto.SucursalResponseDTO;
import com.rentacar.ms_sucursales.model.Sucursal;
import com.rentacar.ms_sucursales.repository.SucursalRepository;

@SpringBootTest
public class SucursalApplicationTests {

    @Mock 
    private SucursalRepository sucursalRepository;
    
    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursalMock;
    private SucursalRequestDTO requestMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        sucursalMock = new Sucursal();
        sucursalMock.setId(1L);
        sucursalMock.setNombre("Sucursal Aeropuerto Santiago");
        sucursalMock.setDireccion("Av. Américo Vespucio 1234");
        sucursalMock.setCiudad("Santiago");
        sucursalMock.setTelefono("+56223456789");

        requestMock = new SucursalRequestDTO();
        requestMock.setNombre("Sucursal Aeropuerto Santiago");
        requestMock.setDireccion("Av. Américo Vespucio 1234");
        requestMock.setCity("Santiago");
        requestMock.setTelefono("+56223456789");
    }

    @Test
    void registrarSucursalTest() {
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalMock);

        SucursalResponseDTO response = sucursalService.registrarSucursal(requestMock);

        assertNotNull(response);
        assertEquals("Sucursal Aeropuerto Santiago", response.getNombre());
        assertEquals("Santiago", response.getCiudad());
    }
}