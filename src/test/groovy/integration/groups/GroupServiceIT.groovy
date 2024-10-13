package integration.groups

import com.example.raid_planner.domain.events.EventDto
import com.example.raid_planner.domain.groups.GroupDto
import com.example.raid_planner.domain.groups.GroupService
import com.example.raid_planner.infrastructure.repository.events.EventEntity
import com.example.raid_planner.infrastructure.repository.events.EventJpaRepository
import com.example.raid_planner.infrastructure.repository.groups.GroupJpaRepository
import com.example.raid_planner.infrastructure.repository.groups.GroupType
import config.RepositoryConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

@SpringBootTest(classes = [GroupService.Impl, RepositoryConfig.class])
@ActiveProfiles('test')
class GroupServiceIT extends Specification {

    @Subject
    @Autowired
    GroupService groupService

    @Autowired
    EventJpaRepository eventJpaRepository
    @Autowired
    GroupJpaRepository groupJpaRepository

    def 'should save new group for event'() {
        given:
            EventDto event = EventDto.builder()
                    .organizerId(UUID.randomUUID())
                    .attendeeId(UUID.randomUUID())
                    .createdAt(LocalDateTime.now())
                    .ready(false)
                    .build()
            eventJpaRepository.save(EventEntity.from(event))


        when:
            GroupDto savedGroup = groupService.createGroupOfType(event.organizerId, GroupType.ARCHER)

        then:
            GroupDto actualGroup = groupJpaRepository.findById(savedGroup.getId()).get().toDto()

            savedGroup == actualGroup
    }

}
