package com.rentacar.ms_pagos.controller;

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

import com.rentacar.ms_pagos.assemblers.PagoModelAssembler;
import com.rentacar.ms_pagos.model.Pago;
import com.rentacar.ms_pagos.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Pagos", description = "API para la gestion de pagos")
@RestController
@RequestMapping("/api/v2/pagos")
public class PagoControllerV2 {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> getAllPagos() {
        List<Pago> pagos = pagoService.obtenerTodos();
        CollectionModel<EntityModel<Pago>> collectionModel = assembler.toCollectionModel(pagos);
        collectionModel.add(linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> getPagoById(@PathVariable Long id) {
        Pago pago = pagoService.findById(id);

        if (pago == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(pago));
    }

    @Operation(summary = "Registrar un nuevo pago", description = "Guarda los datos del pago en la base de datos validando sus atributos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> registrarPago(@Valid @RequestBody Pago pago) {
        log.info("Iniciando procesamiento de pago para la reserva ID: {}", pago.getReservaId());
        
        Pago nuevoPago = pagoService.save(pago);
        EntityModel<Pago> entityModel = assembler.toModel(nuevoPago);
        
        log.info("Pago registrado exitosamente con ID: {} - Estado: {}", nuevoPago.getId(), nuevoPago.getEstado());
        
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> updatePago(@PathVariable Long id, @RequestBody Pago pago) {
        pago.setId(id);
        Pago updated = pagoService.save(pago);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> patchPago(@PathVariable Long id, @RequestBody Pago pago) {
        Pago updated = pagoService.patchPago(id, pago);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        Pago existing = pagoService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        pagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}