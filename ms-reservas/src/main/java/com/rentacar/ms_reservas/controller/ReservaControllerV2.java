package com.rentacar.ms_reservas.controller;

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

import com.rentacar.ms_reservas.assemblers.ReservaModelAssembler;
import com.rentacar.ms_reservas.model.Reserva;
import com.rentacar.ms_reservas.service.ReservaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Reservas V2", description = "API para gestion de reservas con hateoas")
@RestController
@RequestMapping("/api/v2/reservas")
public class ReservaControllerV2 {

    @Autowired 
    private ReservaService reservaService;
    
    @Autowired 
    private ReservaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Reserva>>> getAllReservas() {
        return ResponseEntity.ok(assembler.toCollectionModel(reservaService.obtenerTodas()).add(linkTo(methodOn(ReservaControllerV2.class).getAllReservas()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> getReservaById(@PathVariable Long id) {
        Reserva r = reservaService.findById(id);
        return (r == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(r));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> crearReserva(@Valid @RequestBody Reserva reserva) {
        EntityModel<Reserva> model = assembler.toModel(reservaService.save(reserva));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        reserva.setId(id);
        return ResponseEntity.ok(assembler.toModel(reservaService.save(reserva)));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> patchReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Reserva updated = reservaService.patchReserva(id, reserva);
        return (updated == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        if (reservaService.findById(id) == null) return ResponseEntity.notFound().build();
        reservaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}