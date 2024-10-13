package unit.events

import com.example.raid_planner.domain.events.EventDto
import com.example.raid_planner.domain.events.EventRepository
import com.example.raid_planner.infrastructure.repository.events.EventJpaRepository
import com.example.raid_planner.infrastructure.repository.events.EventRepositoryImpl
import com.example.raid_planner.infrastructure.exceptions.EventNotFoundException
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class EventRepositoryUT extends Specification {

    EventJpaRepository eventJpaRepository = Mock()

    @Subject
    EventRepository eventRepository = new EventRepositoryImpl(eventJpaRepository)

    def 'should return correct event'() {
        given:
            EventDto event = EventDto.builder()
                    .attendeeId(UUID.randomUUID())
                    .organizerId(UUID.randomUUID())
                    .plannedStart(LocalDateTime.now())
                    .createdAt(LocalDateTime.now().plusDays(2l))
                    .ready(false)
                    .build()
            eventJpaRepository.findByOrganizerIdOrAttendeeId(event.attendeeId, event.attendeeId) >> event

        when:
            EventDto retrievedEvent = eventRepository.getByUUID(event.attendeeId)

        then:
            retrievedEvent == event

    }


    def 'should throw EventNotFoundException when trying to find event by id that do not exist'() {
        given:
            UUID uuid = UUID.randomUUID()
            eventJpaRepository.findByOrganizerIdOrAttendeeId(uuid, uuid) >> null

        when:
            eventRepository.getByUUID(uuid)

        then:
            thrown(EventNotFoundException)
    }
}
