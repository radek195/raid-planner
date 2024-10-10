package com.example.raid_planner.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(String message, HttpStatus status, ZonedDateTime timestamp) {
}
