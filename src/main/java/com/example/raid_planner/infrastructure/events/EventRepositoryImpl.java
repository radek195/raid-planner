package com.example.raid_planner.infrastructure.events;

import com.example.raid_planner.domain.events.EventDto;
import com.example.raid_planner.domain.events.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
