package com.example.raid_planner.domain.groups;

import com.example.raid_planner.domain.events.EventService;
import com.example.raid_planner.infrastructure.exceptions.NotFoundException;
import com.example.raid_planner.infrastructure.repository.attender.AttenderEntity;
import com.example.raid_planner.infrastructure.repository.attender.AttenderJpaRepository;
import com.example.raid_planner.infrastructure.repository.events.EventEntity;
import com.example.raid_planner.infrastructure.repository.groups.GroupEntity;
import com.example.raid_planner.infrastructure.repository.groups.GroupJpaRepository;
import com.example.raid_planner.infrastructure.repository.groups.GroupType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupService {

    GroupDto createGroupOfType(UUID eventUUID, GroupType type);

    void deleteGroup(UUID uuid, Long id);

    AttenderDto appendAttender(Long groupId, AttenderDto attenderDto);

    void signUpAttender(Long attenderId, AttenderDto attenderDto);

    @Service
    @RequiredArgsConstructor
    class Impl implements GroupService {

        private final EventService eventService;

        private final GroupJpaRepository groupJpaRepository;
        private final AttenderJpaRepository attenderJpaRepository;

        public GroupDto createGroupOfType(UUID uuid, GroupType type) {
            EventEntity event = eventService.getEventByUUIDForOrganizer(uuid);
            GroupEntity group = GroupEntity.builder()
                    .groupType(type)
                    .ready(false)
                    .event(event)
                    .build();
            return groupJpaRepository.save(group).toDto();
        }

        public void deleteGroup(UUID uuid, Long id) {
            eventService.getEventByUUIDForOrganizer(uuid);
            groupJpaRepository.deleteById(id);
        }

        public AttenderDto appendAttender(Long groupId, AttenderDto attenderDto) {
            GroupEntity groupEntity = getGroupById(groupId);
            List<AttenderEntity> attenderEntityList = groupEntity.getAttenders();
            if (attenderEntityList.size() >= 9) {
                throw new UnsupportedOperationException("Max group size is 9.");
            }
            AttenderEntity attenderEntity = AttenderEntity.from(attenderDto);
            attenderEntity.setGroup(groupEntity);

            attenderJpaRepository.save(attenderEntity);

            return attenderEntity.toDto();
        }

        public void signUpAttender(Long attenderId, AttenderDto attenderDto) {
            Optional<AttenderEntity> optionalAttender = attenderJpaRepository.findById(attenderId);
            if (optionalAttender.isEmpty()) {
                throw new NotFoundException("Could not find attender with id " + attenderId);
            }

            AttenderEntity attenderEntity = optionalAttender.get();
            attenderEntity.setActualProfession(attenderDto.getActualProfession());
            attenderEntity.setNickname(attenderDto.getNickname());
            attenderJpaRepository.save(attenderEntity);
        }

        public GroupEntity getGroupById(Long groupId) {
            Optional<GroupEntity> optionalGroup = groupJpaRepository.findById(groupId);
            if (optionalGroup.isEmpty()) {
                throw new NotFoundException("Could not find group with id " + groupId);
            }
            return optionalGroup.get();
        }


    }
}