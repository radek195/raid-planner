package integration

import com.example.raid_planner.domain.events.EventDto
import com.example.raid_planner.domain.events.EventRepository
import com.example.raid_planner.infrastructure.events.EventEntity
import com.example.raid_planner.infrastructure.events.EventJpaRepository
import config.RepositoryConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

@SpringBootTest(classes = [RepositoryConfig.class])
@ActiveProfiles('test')
class EventRepositoryIT extends Specification {

    @Subject
    @Autowired
    EventRepository eventRepository

    @Autowired
    EventJpaRepository eventJpaRepository

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
            EventDto eventDto = eventRepository.updateEventReadiness(plannedTime, organizerUUID)

        then:
            with(eventDto) {
                ready
                plannedStart == plannedTime
            }
    }
}