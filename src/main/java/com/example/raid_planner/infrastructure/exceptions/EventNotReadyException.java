package com.example.raid_planner.infrastructure.exceptions;

public class EventNotReadyException extends RuntimeException {
    public EventNotReadyException(String message) {
        super(message);
    }
}
