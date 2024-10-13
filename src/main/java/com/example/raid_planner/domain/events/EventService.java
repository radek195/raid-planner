package com.example.raid_planner.domain.events;

import com.example.raid_planner.infrastructure.exceptions.EventNotFoundException;
import com.example.raid_planner.infrastructure.exceptions.EventNotReadyException;
import com.example.raid_planner.infrastructure.repository.events.EventEntity;
import com.example.raid_planner.infrastructure.repository.events.EventJpaRepository;
import com.example.raid_planner.infrastructure.utils.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


public interface EventService {

    EventDto initializeEvent();

    EventDto getByUUID(UUID uuid);

    EventDto updateEventReadiness(LocalDateTime plannedStart, UUID uuid);

    @Service
    @RequiredArgsConstructor
    class Impl implements EventService {

        private final EventJpaRepository eventJpaRepository;
        private final TimeService timeService;

        public EventDto initializeEvent() {
            EventEntity event = EventEntity.builder()
                    .organizerId(UUID.randomUUID())
                    .attendeeId(UUID.randomUUID())
                    .createdAt(timeService.now())
                    .ready(false)
                    .build();

            return eventJpaRepository
                    .save(event)
                    .toDto();
        }

        public EventDto getByUUID(UUID uuid) {
            EventEntity event = eventJpaRepository.findByOrganizerIdOrAttendeeId(uuid, uuid);
            if (event == null) {
                throw new EventNotFoundException("Could not find event with UUID " + uuid);
            }
            if (event.getAttendeeId().equals(uuid) && !event.isReady()) {
                throw new EventNotReadyException("Event is not ready yet.");
            }
            return event.toDto();
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
}
