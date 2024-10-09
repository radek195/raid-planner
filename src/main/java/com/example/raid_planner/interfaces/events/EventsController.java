package com.example.raid_planner.interfaces.events;

import com.example.raid_planner.domain.events.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @PostMapping
    public EventInitializedResponse postNewEvent() {
        return EventInitializedResponse.from(eventService.initializeEvent());
    }
}
