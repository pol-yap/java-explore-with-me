package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.event.Location;

@Getter
@AllArgsConstructor
public class EventFullDto extends EventShortDto {

    protected String createdOn;

    protected String description;

    protected Location location;

    protected int participantLimit;

    protected String publishedOn;

    protected boolean requestModeration;

    protected String state;
}
