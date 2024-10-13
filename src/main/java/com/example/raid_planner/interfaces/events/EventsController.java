package com.example.raid_planner.interfaces.events;

import com.example.raid_planner.domain.events.EventDto;
import com.example.raid_planner.domain.events.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventInitializedResponse postNewEvent() {
        return EventInitializedResponse.from(eventService.initializeEvent());
    }

    @GetMapping(path = "/{uuid}")
    public EventResponse getEvent(@PathVariable UUID uuid) {
        EventDto eventDto = eventService.getByUUID(uuid);
        return EventResponse.from(eventDto);
    }

    @PatchMapping(path = "/{uuid}")
    public EventResponse updateEventReady(
            @PathVariable UUID uuid,
            @RequestBody EventReadyRequest eventReady) {

        EventDto eventDto = eventService.updateEventReadiness(eventReady.getPlannedStart(), uuid);
        return EventResponse.from(eventDto);
    }
}






