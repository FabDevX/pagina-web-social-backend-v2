package com.infsis.socialpagebackend.events.services;

import com.infsis.socialpagebackend.events.models.EventRegistration;
import com.infsis.socialpagebackend.events.repositories.EventRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRegistrationService {
    @Autowired
    private EventRegistrationRepository registrationRepository;

    public List<EventRegistration> getRegistrationsByEventUuid(String eventUuid) {
        return registrationRepository.findByEvent_Uuid(eventUuid);
    }

    public EventRegistration createRegistration(EventRegistration registration) {
        return registrationRepository.save(registration);
    }
}

