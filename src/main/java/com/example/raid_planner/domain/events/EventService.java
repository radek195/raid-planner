package com.example.raid_planner.domain.events;

import com.example.raid_planner.infrastructure.repository.events.EventEntity;
import com.example.raid_planner.infrastructure.exceptions.EventNotReadyException;
import com.example.raid_planner.infrastructure.utils.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

public interface EventService {

    EventDto initializeEvent();

    EventDto getEventByUUID(UUID uuid);

    EventDto eventReady(UUID uuid, LocalDateTime plannedStart);

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
                    .ready(false)
                    .build();
            return eventRepository.save(EventEntity.from(newEvent));
        }

        public EventDto getEventByUUID(UUID uuid) {
            EventDto event = eventRepository.getByUUID(uuid);
            if (event.getAttendeeId().equals(uuid) && !event.isReady()) {
                throw new EventNotReadyException("Event is not ready yet.");
            }
            return event;
        }


        public EventDto eventReady(UUID uuid, LocalDateTime plannedStart) {
            return eventRepository.updateEventReadiness(plannedStart, uuid);
        }

    }
}




























