package com.example.raid_planner.infrastructure.repository.events;

import com.example.raid_planner.domain.events.EventDto;
import com.example.raid_planner.domain.events.EventRepository;
import com.example.raid_planner.infrastructure.exceptions.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EventRepositoryImpl implements EventRepository {
    private final EventJpaRepository eventJpaRepository;

    public EventDto save(EventEntity eventEntity) {
        return eventJpaRepository
                .save(eventEntity)
                .toDto();
    }

    public EventDto getByUUID(UUID uuid) {
        EventDto event = eventJpaRepository.findByOrganizerIdOrAttendeeId(uuid, uuid);
        if (event == null) {
            throw new EventNotFoundException("Could not find event with UUID " + uuid);
        }
        return event;
    }

    @Transactional
    public EventDto updateEventReadiness(LocalDateTime plannedStart, UUID uuid) {
        EventEntity event = eventJpaRepository.findByOrganizerId(uuid);
        if (event == null) {
            throw new EventNotFoundException("Could not find event with UUID " + uuid);
        }
        event.setPlannedStart(plannedStart);
        event.setReady(true);
        return event.toDto();
    }


}
