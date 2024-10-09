package com.example.raid_planner.infrastructure.exceptions.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public record ApiException(String message, HttpStatus status, ZonedDateTime timestamp) {
}
