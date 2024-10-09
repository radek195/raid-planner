package com.example.raid_planner.domain.events;

import com.example.raid_planner.infrastructure.events.EventEntity;
import com.example.raid_planner.infrastructure.utils.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface EventService {

    EventDto initializeEvent();

    @Service
    @RequiredArgsConstructor
    class Impl implements EventService {

        private final TimeService timeService;
        private final EventRepository eventRepository;

        public EventDto initializeEvent() {
            EventDto newEvent = EventDto.builder()
                    .organizerId(UUID.randomUUID())
                    .attendeeId(UUID.randomUUID())
                    .createdAt(timeService.now())
                    .isReady(false)
                    .build();
            return eventRepository.save(EventEntity.from(newEvent));
        }
    }
}
