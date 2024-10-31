package integration.groups

import com.example.raid_planner.domain.AttenderDto
import com.example.raid_planner.domain.events.EventDto
import com.example.raid_planner.domain.groups.GroupDto
import com.example.raid_planner.domain.groups.GroupService
import com.example.raid_planner.infrastructure.repository.attender.AttenderEntity
import com.example.raid_planner.infrastructure.repository.attender.Profession
import com.example.raid_planner.infrastructure.repository.events.EventEntity
import com.example.raid_planner.infrastructure.repository.events.EventJpaRepository
import com.example.raid_planner.infrastructure.repository.groups.GroupEntity
import com.example.raid_planner.infrastructure.repository.groups.GroupJpaRepository
import com.example.raid_planner.infrastructure.repository.groups.GroupType
import config.RepositoryConfig
import groovy.sql.GroovyRowResult
import integration.SqlHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

@SpringBootTest(classes = [GroupService.Impl, RepositoryConfig.class])
@ActiveProfiles('test')
class GroupServiceIT extends Specification {

    SqlHelper sqlHelper

    @Subject
    @Autowired
    GroupService groupService

    @Autowired
    EventJpaRepository eventJpaRepository
    @Autowired
    GroupJpaRepository groupJpaRepository

    def setup() {
        sqlHelper = new SqlHelper()
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

    @Sql("classpath:sql/oneEventOneGroup.sql")
    def 'should append new attender'() {
        given:
            AttenderDto attender = AttenderDto.builder()
                    .requiredProfession(Profession.MAGE)
                    .actualProfession("spellsinger")
                    .nickname("Froz3n")
                    .build()
        when:
            groupService.appendAttender(1, attender)

        then:
            GroovyRowResult actualAttender = sqlHelper.selectAllFromAttendersByGroupId(1).first()
            actualAttender.get('required_profession') == attender.requiredProfession as String
            actualAttender.get('actual_profession') == attender.actualProfession
            actualAttender.get('nickname') == attender.nickname
    }

    @Sql("classpath:sql/oneEventOneGroup.sql")
    def 'should append 2 new attenders'() {
        given:
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
            groupService.appendAttender(1, attender)
            groupService.appendAttender(1, attender2)

        then:
            List<GroovyRowResult> actualAttenderList = sqlHelper.selectAllFromAttendersByGroupId(1)
            actualAttenderList.size() == 2
    }

    @Sql("classpath:sql/oneEventOneGroup.sql")
    def 'should append attender with null fields'() {
        given:
            AttenderDto attender = AttenderDto.builder()
                    .requiredProfession(Profession.MAGE)
                    .build()

        when:
            groupService.appendAttender(1, attender)

        then:
            List<GroovyRowResult> actualAttenderList = sqlHelper.selectAllFromAttendersByGroupId(1)
            actualAttenderList.size() == 1
    }

    @Sql("classpath:sql/oneEventOneGroup9Attenders.sql")
    def 'should throw error when trying to add 10th attender'() {
        given:
            AttenderDto attender = AttenderDto.builder()
                    .requiredProfession(Profession.MAGE)
                    .actualProfession("spellsinger")
                    .nickname("Froz3n")
                    .build()
        when:
            groupService.appendAttender(1, attender)

        then:
            thrown(UnsupportedOperationException)
    }
}
