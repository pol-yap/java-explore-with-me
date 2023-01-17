package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.event.Location;

@Getter
@AllArgsConstructor
public class AdminUpdateEventRequest extends UpdateEventRequest {

    protected Location location;

    protected Boolean requestModeration;
}
