package com.infsis.socialpagebackend.events.services;

import com.infsis.socialpagebackend.events.models.Event;
import com.infsis.socialpagebackend.events.models.EventRegistration;
import com.infsis.socialpagebackend.events.repositories.EventRegistrationRepository;
import com.infsis.socialpagebackend.events.repositories.EventRepository;
import com.infsis.socialpagebackend.events.mappers.EventRegistrationMapper;
import com.infsis.socialpagebackend.events.dtos.EventRegistrationResponseDTO;
import com.infsis.socialpagebackend.events.dtos.EventRegistrationListDTO;
import com.infsis.socialpagebackend.authentication.repositories.UserRepository;
import com.infsis.socialpagebackend.authentication.models.Users;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class EventRegistrationService {
    @Autowired
    private EventRegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventRegistrationMapper eventRegistrationMapper;

    @Autowired
    private UserRepository userRepository;

    public List<EventRegistrationListDTO> getRegistrationsByEventUuid(String eventUuid) {
        Optional<Event> eventOpt = eventRepository.findByUuid(eventUuid);
        if (eventOpt.isEmpty()) {
            throw new EntityNotFoundException("Evento no encontrado: " + eventUuid);
        }
        List<EventRegistration> registrations = registrationRepository.findByEvent_Uuid(eventUuid);
        return eventRegistrationMapper.toListDTO(registrations);
    }

    public EventRegistration createRegistration(EventRegistration registration) {
        return registrationRepository.save(registration);
    }

    public EventRegistrationResponseDTO registerStudentToEvent(String eventUuid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
        Optional<Event> eventOpt = eventRepository.findByUuid(eventUuid);
        if (eventOpt.isEmpty()) {
            throw new RuntimeException("Evento no encontrado: " + eventUuid);
        }
        Event event = eventOpt.get();
        boolean alreadyRegistered = registrationRepository.findByEvent_Uuid(eventUuid)
            .stream()
            .anyMatch(r -> r.getUser().getUuid().equals(user.getUuid()));
        if (alreadyRegistered) {
            throw new IllegalArgumentException("El usuario ya est�� registrado en este evento.");
        }
        EventRegistration registration = eventRegistrationMapper.toEntity(event, user);
        EventRegistration saved = registrationRepository.save(registration);
        return eventRegistrationMapper.toResponseDTO(saved);
    }
}