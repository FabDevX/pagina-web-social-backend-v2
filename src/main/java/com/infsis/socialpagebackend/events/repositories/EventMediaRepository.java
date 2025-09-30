package com.infsis.socialpagebackend.events.repositories;

import com.infsis.socialpagebackend.events.models.EventMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventMediaRepository extends JpaRepository<EventMedia, Integer> {
    List<EventMedia> findByEvent_Uuid(String eventUuid);
}

