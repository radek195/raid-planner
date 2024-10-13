package com.example.raid_planner.domain.events;

import com.example.raid_planner.domain.groups.GroupDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class EventDto {
    private Long id;
    private UUID organizerId;
    private UUID attendeeId;
    private LocalDateTime createdAt;
    private LocalDateTime plannedStart;
    private boolean ready;

    private List<GroupDto> groups;
}
