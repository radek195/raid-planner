package com.example.raid_planner.interfaces.events;

import com.example.raid_planner.domain.events.EventDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class EventInitializedResponse {
    private UUID organizerIdentifier;
    private UUID attendeeIdentifier;
    private LocalDateTime createdAt;

    public static EventInitializedResponse from(EventDto eventDto) {
            return EventInitializedResponse.builder()
                    .organizerIdentifier(eventDto.getOrganizerId())
                    .attendeeIdentifier(eventDto.getAttendeeId())
                    .createdAt(eventDto.getCreatedAt())
                    .build();
    }
}
