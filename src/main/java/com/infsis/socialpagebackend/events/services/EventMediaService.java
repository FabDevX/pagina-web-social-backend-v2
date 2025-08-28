package com.infsis.socialpagebackend.events.services;

import com.infsis.socialpagebackend.events.models.EventMedia;
import com.infsis.socialpagebackend.events.repositories.EventMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventMediaService {
    @Autowired
    private EventMediaRepository mediaRepository;

    public List<EventMedia> getMediaByEventUuid(String eventUuid) {
        return mediaRepository.findByEvent_Uuid(eventUuid);
    }

    public EventMedia createMedia(EventMedia media) {
        return mediaRepository.save(media);
    }
}

