package com.example.raid_planner.infrastructure.repository.groups;

import com.example.raid_planner.domain.groups.GroupDto;
import com.example.raid_planner.domain.groups.GroupRepository;
import com.example.raid_planner.infrastructure.repository.events.EventEntity;
import com.example.raid_planner.infrastructure.repository.events.EventJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GroupRepositoryImpl implements GroupRepository {

    private final EventJpaRepository eventJpaRepository;
    private final GroupJpaRepository groupJpaRepository;

    @Override
    public GroupDto saveNewGroup(UUID eventUUID, GroupDto groupDto) {
        EventEntity event = eventJpaRepository.findByOrganizerId(eventUUID);
        GroupEntity group = GroupEntity.from(groupDto);
        group.setEvent(event);
        return groupJpaRepository.save(group).toDto();
    }
}
