package com.example.raid_planner.interfaces.events;

import com.example.raid_planner.domain.events.EventDto;
import com.example.raid_planner.domain.groups.GroupDto;
import com.example.raid_planner.interfaces.groups.GroupResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class EventResponse {
    private Long id;
    private UUID organizerId;
    private UUID attendeeId;
    private LocalDateTime createdAt;
    private LocalDateTime plannedStart;
    private boolean ready;

    private List<GroupResponse> groups;

    public static EventResponse from(EventDto event) {
        return EventResponse.builder()
                .id(event.getId())
                .organizerId(event.getOrganizerId())
                .attendeeId(event.getAttendeeId())
                .createdAt(event.getCreatedAt())
                .plannedStart(event.getPlannedStart())
                .ready(event.isReady())
                .groups(mapGroups(event.getGroups()))
                .build();
    }

    private static List<GroupResponse> mapGroups(List<GroupDto> dtos) {
        return dtos.stream().map(GroupResponse::from).toList();
    }
}
