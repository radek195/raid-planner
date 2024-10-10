package com.example.raid_planner.infrastructure.repository.events;

import com.example.raid_planner.domain.events.EventDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventEntity {

    @Id
    @SequenceGenerator(
            name = "event_sequence",
            sequenceName = "event_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "event_sequence"
    )
    private Long id;
    private UUID organizerId;
    private UUID attendeeId;
    private LocalDateTime createdAt;
    private LocalDateTime plannedStart;
    private boolean ready;




    public static EventEntity from(EventDto eventDto) {
        return EventEntity.builder()
                .id(eventDto.getId())
                .organizerId(eventDto.getOrganizerId())
                .attendeeId(eventDto.getAttendeeId())
                .createdAt(eventDto.getCreatedAt())
                .plannedStart(eventDto.getPlannedStart())
                .ready(eventDto.isReady())
                .build();
    }

    public EventDto toDto() {
        return EventDto.builder()
                .id(this.getId())
                .organizerId(this.getOrganizerId())
                .attendeeId(this.getAttendeeId())
                .createdAt(this.getCreatedAt())
                .plannedStart(this.getPlannedStart())
                .ready(this.ready)
                .build();
    }
}
