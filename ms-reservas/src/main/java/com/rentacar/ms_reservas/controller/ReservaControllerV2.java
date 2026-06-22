package com.rentacar.ms_reservas.controller;

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

import com.rentacar.ms_reservas.assemblers.ReservaModelAssembler;
import com.rentacar.ms_reservas.model.Reserva;
import com.rentacar.ms_reservas.service.ReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Reservas", description = "API para la gestion de reservas")
@RestController
@RequestMapping("/api/v2/reservas")
public class ReservaControllerV2 {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Reserva>>> getAllReservas() {
        List<Reserva> reservas = reservaService.obtenerTodas();
        CollectionModel<EntityModel<Reserva>> collectionModel = assembler.toCollectionModel(reservas);
        
        collectionModel.add(linkTo(methodOn(ReservaControllerV2.class).getAllReservas()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> getReservaById(@PathVariable Long id) {
        Reserva reserva = reservaService.findById(id);

        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(reserva));
    }

    @Operation(summary = "Registrar una nueva reserva", description = "Guarda los datos de una reserva en la base de datos validando sus atributos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> crearReserva(@Valid @RequestBody Reserva reserva) {
        log.info("Iniciando proceso de reserva para el usuario ID: {}", reserva.getUsuarioId());
        
        Reserva nuevaReserva = reservaService.save(reserva);
        EntityModel<Reserva> entityModel = assembler.toModel(nuevaReserva);
        
        log.info("Reserva exitosa con ID: {}. Monto total: {}", nuevaReserva.getId(), nuevaReserva.getMontoTotal());
        
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        reserva.setId(id);
        Reserva updated = reservaService.save(reserva);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> patchReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Reserva updated = reservaService.patchReserva(id, reserva);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        Reserva existing = reservaService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        reservaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}