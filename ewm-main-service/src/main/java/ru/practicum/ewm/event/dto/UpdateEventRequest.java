package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UpdateEventRequest {

    protected String annotation;

    protected Long category;

    protected String description;

    protected String eventDate;

    protected Boolean paid;

    protected Integer participantLimit;

    protected String title;
}
