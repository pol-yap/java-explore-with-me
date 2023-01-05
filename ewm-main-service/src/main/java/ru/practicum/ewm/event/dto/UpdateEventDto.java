package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateEventDto {

    private final String annotation;

    private final long category;

    private final String description;

    private final String eventDate;

    private final long eventId;

    private final boolean paid;

    private final int participantLimit;

    private final String title;
}
