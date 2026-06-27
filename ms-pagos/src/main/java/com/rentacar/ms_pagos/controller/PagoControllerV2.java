package com.rentacar.ms_pagos.controller;

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

import com.rentacar.ms_pagos.assemblers.PagoModelAssembler;
import com.rentacar.ms_pagos.model.Pago;
import com.rentacar.ms_pagos.service.PagoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Pagos V2", description = "API para la gestión de pagos")
@RestController
@RequestMapping("/api/v2/pagos")
public class PagoControllerV2 {

    @Autowired 
    private PagoService pagoService;

    @Autowired 
    private PagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> getAllPagos() {
        return ResponseEntity.ok(assembler.toCollectionModel(pagoService.obtenerTodos()).add(linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> getPagoById(@PathVariable Long id) {
        Pago p = pagoService.findById(id);
        return (p == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(p));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> registrarPago(@Valid @RequestBody Pago pago) {
        log.info("Procesando pago V2 para reserva: {}", pago.getReservaId());
        EntityModel<Pago> model = assembler.toModel(pagoService.save(pago));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> updatePago(@PathVariable Long id, @RequestBody Pago pago) {
        pago.setId(id);
        return ResponseEntity.ok(assembler.toModel(pagoService.save(pago)));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> patchPago(@PathVariable Long id, @RequestBody Pago pago) {
        Pago updated = pagoService.patchPago(id, pago);
        return (updated == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        if (pagoService.findById(id) == null) return ResponseEntity.notFound().build();
        pagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}