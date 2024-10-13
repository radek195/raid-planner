package integration.events

import com.example.raid_planner.domain.events.EventDto
import com.example.raid_planner.domain.events.EventRepository
import com.example.raid_planner.domain.events.EventService
import config.RepositoryConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@SpringBootTest(classes = [EventService.Impl, RepositoryConfig.class])
@ActiveProfiles('test')
class EventServiceIT extends Specification {

    @Subject
    @Autowired
    EventService eventService

    @Autowired
    EventRepository eventRepository

    def 'should initialize one new event record'() {
        given:
            eventRepository.findAll().size() == 0

        when:
            eventService.initializeEvent()

        then:
            eventRepository.findAll().size() == 1
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
}
