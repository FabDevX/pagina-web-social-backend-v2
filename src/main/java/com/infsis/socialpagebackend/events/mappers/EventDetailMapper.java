package com.infsis.socialpagebackend.events.mappers;

import com.infsis.socialpagebackend.events.dtos.EventDetailDTO;
import com.infsis.socialpagebackend.events.dtos.EventUserDTO;
import com.infsis.socialpagebackend.events.models.Event;
import com.infsis.socialpagebackend.authentication.models.Users;
import org.springframework.stereotype.Component;

@Component
public class EventDetailMapper {
    public EventDetailDTO toDTO(Event event) {
        EventDetailDTO dto = new EventDetailDTO();
        dto.setUuid(event.getUuid());
        dto.setTitle(event.getTitle());
        dto.setLocation(event.getLocation());
        dto.setDescription(event.getDescription());
        dto.setStartDate(event.getStartDate());
        dto.setEndDate(event.getEndDate());
        dto.setMaxCapacity(event.getMaxCapacity());
        dto.setIsPublic(event.getIsPublic());
        dto.setRequiresRegistration(event.getRequiresRegistration());
        dto.setStatus(event.getStatus());
        dto.setCoverImagePath(event.getCoverImagePath());
        dto.setCreator(toUserShortDTO(event.getCreator()));
        return dto;
    }

    private EventUserDTO toUserShortDTO(Users user) {
        if (user == null) return null;
        EventUserDTO dto = new EventUserDTO();
        dto.setUuid(user.getUuid());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setLastName(user.getLastName());
        return dto;
    }
}

