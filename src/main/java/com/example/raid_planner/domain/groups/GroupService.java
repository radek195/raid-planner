package com.example.raid_planner.domain.groups;

import com.example.raid_planner.infrastructure.exceptions.EventNotFoundException;
import com.example.raid_planner.infrastructure.repository.events.EventEntity;
import com.example.raid_planner.infrastructure.repository.events.EventJpaRepository;
import com.example.raid_planner.infrastructure.repository.groups.GroupEntity;
import com.example.raid_planner.infrastructure.repository.groups.GroupJpaRepository;
import com.example.raid_planner.infrastructure.repository.groups.GroupType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface GroupService {

    GroupDto createGroupOfType(UUID eventUUID, GroupType type);

    void deleteGroup(UUID uuid, Long id);

    @Service
    @RequiredArgsConstructor
    class Impl implements GroupService {

        private final EventJpaRepository eventJpaRepository;
        private final GroupJpaRepository groupJpaRepository;

        public GroupDto createGroupOfType(UUID uuid, GroupType type) {
            EventEntity event = eventJpaRepository.findByOrganizerId(uuid);
            if (event == null) {
                throw new EventNotFoundException("Could not find event with UUID " + uuid);
            }
            GroupEntity group = GroupEntity.builder()
                    .groupType(type)
                    .ready(false)
                    .event(event)
                    .build();
            return groupJpaRepository.save(group).toDto();
        }

        @Override
        public void deleteGroup(UUID uuid, Long id) {
            EventEntity event = eventJpaRepository.findByOrganizerId(uuid);
            if (event == null) {
                throw new EventNotFoundException("Could not find event with UUID " + uuid);
            }
            groupJpaRepository.deleteById(id);
        }
    }
}