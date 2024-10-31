package com.example.raid_planner.domain.groups;

import com.example.raid_planner.domain.AttenderDto;
import com.example.raid_planner.infrastructure.exceptions.NotFoundException;
import com.example.raid_planner.infrastructure.repository.attender.AttenderEntity;
import com.example.raid_planner.infrastructure.repository.attender.AttenderJpaRepository;
import com.example.raid_planner.infrastructure.repository.events.EventEntity;
import com.example.raid_planner.infrastructure.repository.events.EventJpaRepository;
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

    @Service
    @RequiredArgsConstructor
    class Impl implements GroupService {

        private final EventJpaRepository eventJpaRepository;
        private final GroupJpaRepository groupJpaRepository;
        private final AttenderJpaRepository attenderJpaRepository;

        public GroupDto createGroupOfType(UUID uuid, GroupType type) {
            EventEntity event = eventJpaRepository.findByOrganizerId(uuid);
            if (event == null) {
                throw new NotFoundException("Could not find event with UUID " + uuid);
            }
            GroupEntity group = GroupEntity.builder()
                    .groupType(type)
                    .ready(false)
                    .event(event)
                    .build();
            return groupJpaRepository.save(group).toDto();
        }

        public void deleteGroup(UUID uuid, Long id) {
            EventEntity event = eventJpaRepository.findByOrganizerId(uuid);
            if (event == null) {
                throw new NotFoundException("Could not find event with UUID " + uuid);
            }
            groupJpaRepository.deleteById(id);
        }

        public AttenderDto appendAttender(Long groupId, AttenderDto attenderDto) {
            Optional<GroupEntity> optionalGroup = groupJpaRepository.findById(groupId);
            if (optionalGroup.isEmpty()) {
                throw new NotFoundException("Could not find group with id " + groupId);
            }
            GroupEntity groupEntity = optionalGroup.get();
            List<AttenderEntity> attenderEntityList = groupEntity.getAttenders();
            if (attenderEntityList.size() >= 9) {
                throw new UnsupportedOperationException("Max group size is 9.");
            }
            AttenderEntity attenderEntity = AttenderEntity.from(attenderDto);
            attenderEntity.setGroup(groupEntity);

            attenderJpaRepository.save(attenderEntity);

            return attenderEntity.toDto();
        }


    }
}