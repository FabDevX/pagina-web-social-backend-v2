package com.infsis.socialpagebackend.events.mappers;

import com.infsis.socialpagebackend.events.models.Event;
import com.infsis.socialpagebackend.events.models.EventRegistration;
import com.infsis.socialpagebackend.authentication.models.Users;
import com.infsis.socialpagebackend.events.dtos.EventRegistrationResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EventRegistrationMapper {
    public EventRegistration toEntity(Event event, Users user) {
        EventRegistration registration = new EventRegistration();
        registration.setEvent(event);
        registration.setUser(user);
        registration.setStatus("REGISTERED");
        registration.setDeleted(false);
        // Los demás campos se inicializan automáticamente en @PrePersist
        return registration;
    }

    public EventRegistrationResponseDTO toResponseDTO(EventRegistration registration) {
        EventRegistrationResponseDTO dto = new EventRegistrationResponseDTO();
        dto.setSuccess(true);
        dto.setMessage("Registro exitoso al evento.");
        dto.setRegistrationUuid(registration.getUuid());
        dto.setEventUuid(registration.getEvent().getUuid());
        dto.setUserUuid(registration.getUser().getUuid());
        dto.setRegistrationDate(registration.getRegistrationDate());
        dto.setStatus(registration.getStatus());
        return dto;
    }
}

