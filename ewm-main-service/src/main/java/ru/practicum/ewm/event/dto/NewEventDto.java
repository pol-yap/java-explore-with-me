package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.common.Dto;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class NewEventDto implements Dto<Event> {

    @NotBlank(message = "Event annotation shouldn't be empty")
    private String annotation;

    @NotNull(message = "Event category id shouldn't be null")
    private Long category;

    @NotBlank(message = "Event description shouldn't be empty")
    private String description;

    @NotBlank(message = "Event date shouldn't be empty")
    private String eventDate;

    @NotNull(message = "Event location shouldn't be null")
    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    @NotBlank(message = "Event title shouldn't be empty")
    @Size(min = 3, max = 120, message = "Event title size should be between 3 and 120 characters")
    private String title;

    @Override
    public Event makeEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Event.builder()
                    .annotation(annotation)
                    .description(description)
                    .eventDate(LocalDateTime.parse(eventDate, formatter))
                    .latitude(location.getLat())
                    .longitude(location.getLon())
                    .paid(paid)
                    .participantLimit(participantLimit)
                    .requestModeration(requestModeration)
                    .title(title)
                    .build();
    }
}
