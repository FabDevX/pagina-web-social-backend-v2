package com.infsis.socialpagebackend.events.controllers;

import com.infsis.socialpagebackend.events.models.EventRegistration;
import com.infsis.socialpagebackend.events.models.EventMedia;
import com.infsis.socialpagebackend.events.models.EventInvitation;
import com.infsis.socialpagebackend.events.services.EventService;
import com.infsis.socialpagebackend.events.services.EventRegistrationService;
import com.infsis.socialpagebackend.events.services.EventMediaService;
import com.infsis.socialpagebackend.events.services.EventInvitationService;
import com.infsis.socialpagebackend.events.dtos.EventDTO;
import com.infsis.socialpagebackend.events.dtos.EventDetailDTO;
import com.infsis.socialpagebackend.events.dtos.EventRegistrationResponseDTO;
import com.infsis.socialpagebackend.events.dtos.EventRegistrationListDTO;
import com.infsis.socialpagebackend.events.mappers.EventRegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventRegistrationService eventRegistrationService;
    @Autowired
    private EventMediaService eventMediaService;
    @Autowired
    private EventInvitationService eventInvitationService;
    @Autowired
    private EventRegistrationMapper eventRegistrationMapper;

    // GET /events: Listar todos los eventos (con paginación y filtros opcionales)
    @GetMapping
    public ResponseEntity<Page<EventDetailDTO>> getAllEvents(Pageable pageable) {
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }

    // GET /events/{uuid}: Obtener los detalles de un evento específico
    @GetMapping("/{uuid}")
    public ResponseEntity<EventDetailDTO> getEventByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(eventService.getEventByUuid(uuid));
    }

    // POST /events: Crear un nuevo evento
    @PostMapping
    public ResponseEntity<EventDetailDTO> createEvent(@RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    // PUT /events/{uuid}: Actualizar un evento existente
    @PutMapping("/{uuid}")
    public ResponseEntity<EventDetailDTO> updateEvent(@PathVariable String uuid, @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.updateEvent(uuid, eventDTO));
    }

    // DELETE /events/{uuid}: Eliminar (borrado lógico) un evento
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String uuid) {
        eventService.deleteEvent(uuid);
        return ResponseEntity.noContent().build();
    }

    // GET /events/{uuid}/registrations: Listar registros de un evento
    @GetMapping("/{uuid}/registrations")
    public ResponseEntity<List<EventRegistrationListDTO>> getEventRegistrations(@PathVariable String uuid) {
        return ResponseEntity.ok(eventRegistrationService.getRegistrationsByEventUuid(uuid));
    }

    // POST /events/{uuid}/registrations: Registrar un usuario a un evento
    @PostMapping("/{uuid}/registrations")
    public ResponseEntity<EventRegistrationResponseDTO> registerUserToEvent(@PathVariable String uuid) {
        EventRegistrationResponseDTO response = eventRegistrationService.registerStudentToEvent(uuid);
        return ResponseEntity.status(201).body(response);
    }

    // GET /events/{uuid}/media: Listar archivos multimedia asociados
    @GetMapping("/{uuid}/media")
    public ResponseEntity<List<EventMedia>> getEventMedia(@PathVariable String uuid) {
        return ResponseEntity.ok(eventMediaService.getMediaByEventUuid(uuid));
    }

    // POST /events/{uuid}/media: Subir archivo multimedia al evento
    @PostMapping("/{uuid}/media")
    public ResponseEntity<EventMedia> uploadEventMedia(@PathVariable String uuid, @RequestBody EventMedia media) {
        //Event event = eventService.getEventByUuid(uuid);
       // media.setEvent(event);
        return ResponseEntity.ok(eventMediaService.createMedia(media));
    }

    // GET /events/{uuid}/invitations: Listar invitaciones del evento
    @GetMapping("/{uuid}/invitations")
    public ResponseEntity<List<EventInvitation>> getEventInvitations(@PathVariable String uuid) {
        return ResponseEntity.ok(eventInvitationService.getInvitationsByEventUuid(uuid));
    }

    // POST /events/{uuid}/invitations: Enviar invitaciones
    @PostMapping("/{uuid}/invitations")
    public ResponseEntity<EventInvitation> sendEventInvitations(@PathVariable String uuid, @RequestBody EventInvitation invitation) {
        //Event event = eventService.getEventByUuid(uuid);
        //invitation.setEvent(event);
        return ResponseEntity.ok(eventInvitationService.createInvitation(invitation));
    }
}
