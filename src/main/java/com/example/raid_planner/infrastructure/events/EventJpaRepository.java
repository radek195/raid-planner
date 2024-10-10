package com.example.raid_planner.infrastructure.events;

import com.example.raid_planner.domain.events.EventDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {

    EventDto findByOrganizerIdOrAttendeeId(UUID organizerId, UUID attendeeId);
}