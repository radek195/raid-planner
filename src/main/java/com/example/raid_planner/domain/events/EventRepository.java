package com.example.raid_planner.domain.events;

import com.example.raid_planner.infrastructure.repository.events.EventEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public interface EventRepository {

    EventDto save(EventEntity eventDto);

    EventDto getByUUID(UUID uuid);

    EventDto updateEventReadiness(LocalDateTime plannedStart, UUID uuid);
}
