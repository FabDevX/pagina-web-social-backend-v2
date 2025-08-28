package com.infsis.socialpagebackend.events.repositories;

import com.infsis.socialpagebackend.events.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findByUuid(String uuid);
    void deleteByUuid(String uuid);
}

