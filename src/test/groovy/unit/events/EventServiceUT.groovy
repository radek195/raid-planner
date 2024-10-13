package unit.events

import com.example.raid_planner.domain.events.EventDto
import com.example.raid_planner.domain.events.EventService

import com.example.raid_planner.infrastructure.exceptions.EventNotReadyException
import com.example.raid_planner.infrastructure.repository.events.EventJpaRepository
import com.example.raid_planner.infrastructure.utils.TimeService
import spock.lang.Specification
import spock.lang.Subject

class EventServiceUT extends Specification {

    TimeService timeService = Mock()
    EventJpaRepository eventJpaRepository = Mock()

    @Subject
    EventService eventService = new EventService.Impl(eventJpaRepository, timeService)

    def 'should return event for organizer'() {
        given:
            EventDto event = getNotReadyEventDto(UUID.randomUUID(), UUID.randomUUID())
            eventJpaRepository.findByOrganizerIdOrAttendeeId(event.organizerId, event.organizerId) >> event

        when:
            EventDto retrievedEvent = eventService.getByUUID(event.organizerId)

        then:
            retrievedEvent == event
    }

    def 'should return event for attendee'() {
        given:
            EventDto event = getReadyEventDto(UUID.randomUUID(), UUID.randomUUID())
            eventJpaRepository.findByOrganizerIdOrAttendeeId(event.attendeeId, event.attendeeId) >> event

        when:
            EventDto retrievedEvent = eventService.getByUUID(event.attendeeId)

        then:
            retrievedEvent == event
    }

    def 'should throw EventNotReadyException when retrieving not ready event by attendee'() {
        given:
            EventDto event = getNotReadyEventDto(UUID.randomUUID(), UUID.randomUUID())
            eventJpaRepository.findByOrganizerIdOrAttendeeId(event.attendeeId, event.attendeeId) >> event

        when:
            eventService.getByUUID(event.attendeeId)

        then:
            thrown(EventNotReadyException)
    }

    def getNotReadyEventDto(UUID organizerId, UUID attendeeId) {
        EventDto.builder()
            .ready(false)
            .organizerId(organizerId)
            .attendeeId(attendeeId)
            .build()
    }

    def getReadyEventDto(UUID organizerId, UUID attendeeId) {
        EventDto.builder()
                .ready(true)
                .organizerId(organizerId)
                .attendeeId(attendeeId)
                .build()
    }
}
