package com.infsis.socialpagebackend.events.repositories;

import com.infsis.socialpagebackend.events.models.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {
    List<EventRegistration> findByEvent_Uuid(String eventUuid);
}

