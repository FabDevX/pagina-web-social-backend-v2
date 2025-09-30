package com.infsis.socialpagebackend.events.services;

import com.infsis.socialpagebackend.events.models.EventInvitation;
import com.infsis.socialpagebackend.events.repositories.EventInvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventInvitationService {
    @Autowired
    private EventInvitationRepository invitationRepository;

    public List<EventInvitation> getInvitationsByEventUuid(String eventUuid) {
        return invitationRepository.findByEvent_Uuid(eventUuid);
    }

    public EventInvitation createInvitation(EventInvitation invitation) {
        return invitationRepository.save(invitation);
    }
}

