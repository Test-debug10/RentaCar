package com.rentacar.ms_mantenimiento.service;

import java.time.LocalDate;

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

import com.rentacar.ms_mantenimiento.client.VehiculoClient;
import com.rentacar.ms_mantenimiento.dto.MantenimientoRequestDTO;
import com.rentacar.ms_mantenimiento.dto.MantenimientoResponseDTO;
import com.rentacar.ms_mantenimiento.model.Mantenimiento;
import com.rentacar.ms_mantenimiento.repository.MantenimientoRepository;

@SpringBootTest
public class MantenimientoServiceTest {

    @Mock
    private MantenimientoRepository mantenimientoRepository;

    @Mock
    private VehiculoClient vehiculoClient;

    @InjectMocks
    private MantenimientoService mantenimientoService;

    private Mantenimiento mantMock;
    private MantenimientoRequestDTO requestMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        mantMock = new Mantenimiento();
        mantMock.setId(1L);
        mantMock.setVehiculoId(5L);
        mantMock.setTipo("PREVENTIVO");
        mantMock.setDescripcion("Cambio de aceite y filtros");
        mantMock.setFechaIngreso(LocalDate.now());
        mantMock.setCosto(45000.0);

        requestMock = new MantenimientoRequestDTO();
        requestMock.setVehiculoId(5L);
        requestMock.setTipo("PREVENTIVO");
        requestMock.setDescripcion("Cambio de aceite y filtros");
        requestMock.setFechaIngreso(LocalDate.now());
        requestMock.setCosto(45000.0);
    }

    @Test
    void registrarMantenimientoExitosoTest() {
        // Simulamos que el Feign client encuentra el auto
        when(vehiculoClient.obtenerVehiculoPorId(5L)).thenReturn(new Object());
        when(mantenimientoRepository.save(any(Mantenimiento.class))).thenReturn(mantMock);

        MantenimientoResponseDTO response = mantenimientoService.registrarMantenimiento(requestMock);

        assertNotNull(response);
        assertEquals("PREVENTIVO", response.getTipo());
        assertEquals(45000.0, response.getCosto());
    }
}