package com.example.raid_planner.domain.groups;

import com.example.raid_planner.infrastructure.repository.attender.Profession;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class AttenderDto {
    private Long id;
    private Profession requiredProfession;
    private String actualProfession;
    private String nickname;
}
