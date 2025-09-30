package com.infsis.socialpagebackend.events.mappers;

import com.infsis.socialpagebackend.events.dtos.EventDTO;
import com.infsis.socialpagebackend.events.models.Event;
import com.infsis.socialpagebackend.authentication.models.Users;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public Event toEntity(EventDTO dto, Users creator) {
        Event event = new Event();
        // event.setInstitution(...); // Dejar comentado
        // event.setCategory(...);    // Dejar comentado
        event.setCreator(creator);
        event.setLocation(dto.getLocation());
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setMaxCapacity(dto.getMaxCapacity());
        event.setIsPublic(dto.getIsPublic());
        event.setRequiresRegistration(dto.getRequiresRegistration());
        event.setStatus(dto.getStatus());
        event.setCoverImagePath(dto.getCoverImagePath());
        return event;
    }
}

