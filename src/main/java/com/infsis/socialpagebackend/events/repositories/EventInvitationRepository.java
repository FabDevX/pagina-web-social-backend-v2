package com.infsis.socialpagebackend.events.repositories;

import com.infsis.socialpagebackend.events.models.EventInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventInvitationRepository extends JpaRepository<EventInvitation, Integer> {
    List<EventInvitation> findByEvent_Uuid(String eventUuid);
}

