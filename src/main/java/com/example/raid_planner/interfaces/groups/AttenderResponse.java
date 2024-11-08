package com.example.raid_planner.interfaces.groups;

import com.example.raid_planner.domain.groups.AttenderDto;
import com.example.raid_planner.domain.groups.GroupDto;
import com.example.raid_planner.infrastructure.repository.attender.Profession;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class AttenderResponse {
    private Long id;
    private Profession requiredProfession;
    private String actualProfession;
    private String nickname;


    public static AttenderResponse from(AttenderDto attenderDto) {
        return AttenderResponse.builder()
                .id(attenderDto.getId())
                .requiredProfession(attenderDto.getRequiredProfession())
                .actualProfession(attenderDto.getActualProfession())
                .nickname(attenderDto.getNickname())
                .build();
    }
}
