package com.example.raid_planner.infrastructure.events;

import com.example.raid_planner.domain.events.EventDto;
import com.example.raid_planner.domain.events.EventRepository;
import com.example.raid_planner.infrastructure.exceptions.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EventRepositoryImpl implements EventRepository {
    private final EventJpaRepository eventJpaRepository;

    @Override
    public EventDto save(EventEntity eventEntity) {
        return eventJpaRepository
                .save(eventEntity)
                .toDto();
    }

    @Override
    public EventDto getByUUID(UUID uuid) {
        EventDto event = eventJpaRepository.findByOrganizerIdOrAttendeeId(uuid, uuid);
        if (event == null) {
            throw new EventNotFoundException("Could not find event with UUID " + uuid);
        }
        return event;
    }


}
