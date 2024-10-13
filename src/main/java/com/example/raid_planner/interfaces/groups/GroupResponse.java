package com.example.raid_planner.interfaces.groups;

import com.example.raid_planner.domain.groups.GroupDto;
import com.example.raid_planner.infrastructure.repository.groups.GroupType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class GroupResponse {
    private Long id;
    private GroupType groupType;
    private Boolean ready;

    public static GroupResponse from(GroupDto groupDto) {
        return GroupResponse.builder()
                .id(groupDto.getId())
                .groupType(groupDto.getGroupType())
                .ready(groupDto.getReady())
                .build();
    }
}
