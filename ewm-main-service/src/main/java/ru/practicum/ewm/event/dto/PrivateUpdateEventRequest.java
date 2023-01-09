package ru.practicum.ewm.event.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PrivateUpdateEventRequest extends UpdateEventRequest {

    @NotNull(message = "Event id shouldn't be null")
    protected Long eventId;
}
