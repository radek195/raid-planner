package com.example.raid_planner.interfaces.groups;

import com.example.raid_planner.infrastructure.repository.attender.Profession;
import lombok.Getter;

@Getter
public class PostedAttenderRequest {
    private Profession requiredProfession;
}
