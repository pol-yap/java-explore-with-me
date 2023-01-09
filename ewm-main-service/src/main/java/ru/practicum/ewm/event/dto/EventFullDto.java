package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.event.Location;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EventFullDto extends EventShortDto {

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createdOn;

    protected String description;

    protected Location location;

    protected Integer participantLimit;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime publishedOn;

    protected Boolean requestModeration;

    protected EventState state;

    public EventFullDto(Event event) {
        super(event);
        createdOn = event.getCreatedOn();
        description = event.getDescription();
        location = new Location(event.getLatitude(), event.getLongitude());
        participantLimit = event.getParticipantLimit();
        publishedOn = event.getPublishedOn();
        requestModeration = event.getRequestModeration();
        state = event.getState();
    }
}
