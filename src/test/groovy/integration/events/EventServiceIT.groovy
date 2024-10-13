package integration.events

import com.example.raid_planner.domain.events.EventDto
import com.example.raid_planner.domain.events.EventService
import com.example.raid_planner.infrastructure.repository.events.EventEntity
import com.example.raid_planner.infrastructure.repository.events.EventJpaRepository
import com.example.raid_planner.infrastructure.utils.TimeService
import config.RepositoryConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@SpringBootTest(classes = [EventService.Impl, TimeService.Impl, RepositoryConfig.class])
@ActiveProfiles('test')
class EventServiceIT extends Specification {

    @Subject
    @Autowired
    EventService eventService

    @Autowired
    EventJpaRepository eventJpaRepository

    def 'should initialize one new event record'() {
        given:
            eventJpaRepository.findAll().size() == 0

        when:
            eventService.initializeEvent()

        then:
            eventJpaRepository.findAll().size() == 1
    }

    def 'should initialize new event with correct values'() {
        when:
            EventDto event = eventService.initializeEvent()

        then:
            with(event) {
                !isReady()
                ChronoUnit.SECONDS.between(createdAt, LocalDateTime.now()) < 5
            }
    }

    def 'should update readiness for event'() {
        given:
            LocalDateTime plannedTime = LocalDateTime.now().plusDays(5)
            UUID organizerUUID = UUID.randomUUID()
            EventEntity entity = EventEntity.builder()
                    .organizerId(organizerUUID)
                    .attendeeId(UUID.randomUUID())
                    .createdAt(LocalDateTime.now())
                    .ready(false)
                    .build()
            eventJpaRepository.save(entity)

        when:
            EventDto eventDto = eventService.updateEventReadiness(plannedTime, organizerUUID)

        then:
            with(eventDto) {
                ready
                plannedStart == plannedTime
            }
    }
}
