package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NewEventDto {

    @NotBlank(message = "Event annotation shouldn't be empty")
    @Size(max = 2000, message = "Event annotation size shouldn't exceed 2000 characters")
    private final String annotation;

    @NotNull(message = "Event category id shouldn't be null")
    private final long category;

    @NotBlank(message = "Event description shouldn't be empty")
    @Size(max = 7000, message = "Event description size shouldn't exceed 7000 characters")
    private final String description;

    @NotNull(message = "Event date shouldn't be null")
    @Future(message = "Event date should be in the future")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime eventDate;

    @NotNull(message = "Event location shouldn't be null")
    private final LocationDto location;

    private final boolean paid;

    private final int participantLimit;

    private final boolean requestModeration;

    @NotBlank(message = "Event title shouldn't be empty")
    @Size(min =3, max = 120, message = "Event title size should be between 3 and 120 characters")
    private final String title;
}
