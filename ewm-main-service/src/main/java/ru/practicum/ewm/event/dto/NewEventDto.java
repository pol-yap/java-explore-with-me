package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.event.Location;

@Getter
@AllArgsConstructor
public class NewEventDto {

    private final String annotation;

    private final long category;

    private final String description;

    private final String eventDate;

    private final Location location;

    private final boolean paid;

    private final int participantLimit;

    private final boolean requestModeration;

    private final String title;
}
