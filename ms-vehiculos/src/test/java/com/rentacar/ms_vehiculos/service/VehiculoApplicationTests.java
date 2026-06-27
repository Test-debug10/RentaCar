package com.rentacar.ms_vehiculos.service;

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

import com.rentacar.ms_vehiculos.model.Vehiculo;
import com.rentacar.ms_vehiculos.repository.VehiculoRepository;

@SpringBootTest
public class VehiculoApplicationTests {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    private Vehiculo vehiculoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vehiculoMock = new Vehiculo();
        vehiculoMock.setId(1L);
        vehiculoMock.setMarca("Toyota");
        vehiculoMock.setModelo("Yaris");
        vehiculoMock.setPatente("RT5678");
        vehiculoMock.setAnio(2022);
        vehiculoMock.setPrecioPorDia(25000.0);
        vehiculoMock.setDisponible(true);
    }

    @Test
    void guardarVehiculoTest() {
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculoMock);

        Vehiculo response = vehiculoService.save(vehiculoMock);
        
        assertNotNull(response);
        assertEquals("Toyota", response.getMarca());
        assertEquals("RT5678", response.getPatente());
        assertEquals(25000.0, response.getPrecioPorDia());
    }
}