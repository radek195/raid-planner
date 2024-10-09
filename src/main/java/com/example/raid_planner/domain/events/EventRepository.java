package com.example.raid_planner.domain.events;

import com.example.raid_planner.infrastructure.events.EventEntity;
import org.springframework.stereotype.Component;

@Component
public interface EventRepository {

    EventDto save(EventEntity eventDto);
}
