package com.example.raid_planner.infrastructure.repository.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {

    EventEntity findByOrganizerIdOrAttendeeId(UUID organizerId, UUID attendeeId);

    EventEntity findByOrganizerId(UUID organizerId);
}