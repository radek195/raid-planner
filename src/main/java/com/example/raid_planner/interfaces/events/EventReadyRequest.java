package com.example.raid_planner.interfaces.events;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventReadyRequest {
    private LocalDateTime plannedStart;
}
