package com.infsis.socialpagebackend.events.services;

import com.infsis.socialpagebackend.events.models.Event;
import com.infsis.socialpagebackend.events.repositories.EventRepository;
import com.infsis.socialpagebackend.events.dtos.EventDTO;
import com.infsis.socialpagebackend.events.mappers.EventMapper;
import com.infsis.socialpagebackend.authentication.models.Users;
import com.infsis.socialpagebackend.authentication.repositories.UserRepository;
import com.infsis.socialpagebackend.events.dtos.EventDetailDTO;
import com.infsis.socialpagebackend.events.mappers.EventDetailMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventDetailMapper eventDetailMapper;

    public Page<EventDetailDTO> getAllEvents(Pageable pageable) {
        Page<Event> events = eventRepository.findAll(pageable);
        return new PageImpl<>(
            events.getContent().stream().map(eventDetailMapper::toDTO).collect(Collectors.toList()),
            pageable,
            events.getTotalElements()
        );
    }

    public EventDetailDTO getEventByUuid(String uuid) {
        Event event = eventRepository.findByUuid(uuid)
            .orElseThrow(() -> new RuntimeException("Evento no encontrado: " + uuid));
        return eventDetailMapper.toDTO(event);
    }

    public EventDetailDTO createEvent(EventDTO eventDTO) {
        // Obtener usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
        // Mapear DTO a entidad y asignar usuario
        Event event = eventMapper.toEntity(eventDTO, user);
        return eventDetailMapper.toDTO(eventRepository.save(event));
    }

    public EventDetailDTO updateEvent(String uuid, EventDTO eventDTO) {
        Event event = eventRepository.findByUuid(uuid)
            .orElseThrow(() -> new RuntimeException("Evento no encontrado: " + uuid));
        // Actualizar campos permitidos
        event.setTitle(eventDTO.getTitle());
        event.setLocation(eventDTO.getLocation());
        event.setDescription(eventDTO.getDescription());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setMaxCapacity(eventDTO.getMaxCapacity());
        event.setIsPublic(eventDTO.getIsPublic());
        event.setRequiresRegistration(eventDTO.getRequiresRegistration());
        event.setStatus(eventDTO.getStatus());
        event.setCoverImagePath(eventDTO.getCoverImagePath());
        eventRepository.save(event);
        return eventDetailMapper.toDTO(event);
    }

    public void deleteEvent(String uuid) {
        Event event = eventRepository.findByUuid(uuid)
            .orElseThrow(() -> new RuntimeException("Evento no encontrado: " + uuid));
        event.setDeleted(true);
        eventRepository.save(event);
    }
}
