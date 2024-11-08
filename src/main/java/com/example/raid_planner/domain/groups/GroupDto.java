package com.example.raid_planner.domain.groups;

import com.example.raid_planner.infrastructure.repository.groups.GroupType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class GroupDto {
    private Long id;
    private GroupType groupType;
    private Boolean ready;

    List<AttenderDto> attenders;
}
