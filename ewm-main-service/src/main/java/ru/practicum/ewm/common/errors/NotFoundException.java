package ru.practicum.ewm.common.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private final long entityId;
    private final String entityType;
}