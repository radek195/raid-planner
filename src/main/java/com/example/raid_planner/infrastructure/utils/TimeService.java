package com.example.raid_planner.infrastructure.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public interface TimeService {

    LocalDateTime now();

    @Service
    class Impl implements TimeService {

        @Override
        public LocalDateTime now() {
            return LocalDateTime.now();
        }
    }
}
