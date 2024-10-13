package com.example.raid_planner.interfaces.groups;

import com.example.raid_planner.infrastructure.repository.groups.GroupType;
import lombok.Getter;

@Getter
public class GroupRequest {

    private GroupType groupType;
}
