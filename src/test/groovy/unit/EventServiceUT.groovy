package unit

import com.example.raid_planner.domain.events.EventDto
import com.example.raid_planner.domain.events.EventRepository
import com.example.raid_planner.domain.events.EventService
import com.example.raid_planner.infrastructure.exceptions.EventNotReadyException
import com.example.raid_planner.infrastructure.utils.TimeService
import spock.lang.Specification
import spock.lang.Subject

class EventServiceUT extends Specification {

    TimeService timeService = Mock()
    EventRepository eventRepository = Mock()

    @Subject
    EventService eventService = new EventService.Impl(timeService, eventRepository)

    def 'should return event for organizer'() {
        given:
            EventDto event = getNotReadyEventDto(UUID.randomUUID(), UUID.randomUUID())
            eventRepository.getByUUID(event.organizerId) >> event

        when:
            EventDto retrievedEvent = eventService.getEventByUUID(event.organizerId)

        then:
            retrievedEvent == event
    }

    def 'should return event for attendee'() {
        given:
            EventDto event = getReadyEventDto(UUID.randomUUID(), UUID.randomUUID())
            eventRepository.getByUUID(event.attendeeId) >> event

        when:
            EventDto retrievedEvent = eventService.getEventByUUID(event.attendeeId)

        then:
            retrievedEvent == event
    }

    def 'should throw EventNotReadyException when retrieving not ready event by attendee'() {
        given:
            EventDto event = getNotReadyEventDto(UUID.randomUUID(), UUID.randomUUID())
            eventRepository.getByUUID(event.attendeeId) >> event

        when:
            eventService.getEventByUUID(event.attendeeId)

        then:
            thrown(EventNotReadyException)
    }

    def getNotReadyEventDto(UUID organizerId, UUID attendeeId) {
        EventDto.builder()
            .isReady(false)
            .organizerId(organizerId)
            .attendeeId(attendeeId)
            .build()
    }

    def getReadyEventDto(UUID organizerId, UUID attendeeId) {
        EventDto.builder()
                .isReady(true)
                .organizerId(organizerId)
                .attendeeId(attendeeId)
                .build()
    }
}
