package com.example.raid_planner.infrastructure.repository.attender;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Profession {
    BUFFER(1),
    BARD(5),
    HEALER(10),
    RECHARGER(15),
    MELEE(20),
    ARCHER(25),
    MAGE(30);

    private int order;
}
