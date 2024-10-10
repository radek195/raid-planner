package com.example.raid_planner.infrastructure.exceptions;

public class IncorrectParametersException extends RuntimeException {
    public IncorrectParametersException(String message) {
        super(message);
    }
}
