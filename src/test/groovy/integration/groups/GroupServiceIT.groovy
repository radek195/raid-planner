package integration.groups

import com.example.raid_planner.domain.events.EventDto
import com.example.raid_planner.domain.events.EventService
import com.example.raid_planner.domain.groups.AttenderDto
import com.example.raid_planner.domain.groups.GroupDto
import com.example.raid_planner.domain.groups.GroupService
import com.example.raid_planner.infrastructure.repository.attender.Profession
import com.example.raid_planner.infrastructure.repository.events.EventEntity
import com.example.raid_planner.infrastructure.repository.events.EventJpaRepository
import com.example.raid_planner.infrastructure.repository.groups.GroupEntity
import com.example.raid_planner.infrastructure.repository.groups.GroupJpaRepository
import com.example.raid_planner.infrastructure.repository.groups.GroupType
import com.example.raid_planner.infrastructure.utils.TimeService
import config.RepositoryConfig
import groovy.sql.GroovyRowResult
import integration.SqlHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

@SpringBootTest(classes = [TimeService.Impl, EventService.Impl, GroupService.Impl, RepositoryConfig.class])
@ActiveProfiles('test')
class GroupServiceIT extends Specification {

    @Shared
    SqlHelper sqlHelper

    @Subject
    @Autowired
    GroupService groupService

    @Autowired
    EventJpaRepository eventJpaRepository
    @Autowired
    GroupJpaRepository groupJpaRepository

    def setupSpec() {
        sqlHelper = new SqlHelper()
    }

    def setup() {
        sqlHelper.cleanDatabase()
    }

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

    def 'should append new attender'() {
        given:
            int id = insertOneEventOneGroup() as int
            AttenderDto attender = AttenderDto.builder()
                    .requiredProfession(Profession.MAGE)
                    .actualProfession("spellsinger")
                    .nickname("Froz3n")
                    .build()
        when:
            groupService.appendAttender(id, attender)

        then:
            GroovyRowResult actualAttender = sqlHelper.selectAllFromAttendersByGroupId(id).first()
            actualAttender.get('required_profession') == attender.requiredProfession as String
            actualAttender.get('actual_profession') == attender.actualProfession
            actualAttender.get('nickname') == attender.nickname
    }

    def 'should append 2 new attenders'() {
        given:
            int id = insertOneEventOneGroup() as int
            AttenderDto attender = AttenderDto.builder()
                    .requiredProfession(Profession.MAGE)
                    .actualProfession("spellsinger")
                    .nickname("Froz3n")
                    .build()

            AttenderDto attender2 = AttenderDto.builder()
                    .requiredProfession(Profession.HEALER)
                    .actualProfession("bishop")
                    .nickname("AidKIT")
                    .build()
        when:
            groupService.appendAttender(id, attender)
            groupService.appendAttender(id, attender2)

        then:
            List<GroovyRowResult> actualAttenderList = sqlHelper.selectAllFromAttendersByGroupId(id)
            actualAttenderList.size() == 2
    }

    def 'should append attender with null fields'() {
        given:
            int id = insertOneEventOneGroup() as int
            AttenderDto attender = AttenderDto.builder()
                    .requiredProfession(Profession.MAGE)
                    .build()

        when:
            groupService.appendAttender(id, attender)

        then:
            List<GroovyRowResult> actualAttenderList = sqlHelper.selectAllFromAttendersByGroupId(id)
            actualAttenderList.size() == 1
    }

    def 'should throw error when trying to add 10th attender'() {
        given:
            int id = insertOneEventOneGroup() as int
            AttenderDto attender = AttenderDto.builder()
                    .requiredProfession(Profession.MAGE)
                    .actualProfession("spellsinger")
                    .nickname("Froz3n")
                    .build()
        when:
            groupService.appendAttender(id, attender)
            groupService.appendAttender(id, attender)
            groupService.appendAttender(id, attender)
            groupService.appendAttender(id, attender)
            groupService.appendAttender(id, attender)
            groupService.appendAttender(id, attender)
            groupService.appendAttender(id, attender)
            groupService.appendAttender(id, attender)
            groupService.appendAttender(id, attender)
            groupService.appendAttender(id, attender)

        then:
            thrown(UnsupportedOperationException)
    }

    Long insertOneEventOneGroup() {
        EventEntity event = eventJpaRepository.save(EventEntity.builder()
                .organizerId(UUID.randomUUID())
                .attendeeId(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .plannedStart(LocalDateTime.now().plusDays(1))
                .ready(false)
                .build()
        )
        GroupEntity group = GroupEntity.builder()
                .groupType(GroupType.MAGE)
                .ready(false)
                .event(event)
                .build()
        return groupJpaRepository.save(group).getId()
    }

}
