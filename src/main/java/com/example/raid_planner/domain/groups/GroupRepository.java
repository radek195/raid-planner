package com.example.raid_planner.domain.groups;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface GroupRepository {
    GroupDto saveNewGroup(UUID eventUUID, GroupDto group);
}