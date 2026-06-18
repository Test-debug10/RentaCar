package com.rentacar.ms_reservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-vehiculos")
public interface VehiculoClient {

    @GetMapping("/api/v2/vehiculos/{id}")
    VehiculoDTO obtenerVehiculoPorId(@PathVariable("id") Long id);
}