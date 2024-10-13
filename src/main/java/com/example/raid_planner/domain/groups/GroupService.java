package com.example.raid_planner.domain.groups;

import com.example.raid_planner.infrastructure.repository.groups.GroupType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface GroupService {
    GroupDto createGroupOfType(UUID eventUUID, GroupType type);

    @Service
    @RequiredArgsConstructor
    class Impl implements GroupService {

        private final GroupRepository groupRepository;

        public GroupDto createGroupOfType(UUID eventUUID, GroupType type) {
            GroupDto group = GroupDto.builder()
                    .groupType(type)
                    .ready(false)
                    .build();
            return groupRepository.saveNewGroup(eventUUID, group);
        }
    }
}
