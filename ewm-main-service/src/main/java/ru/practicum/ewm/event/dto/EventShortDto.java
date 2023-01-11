package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    protected String annotation;

    protected CategoryDto category;

    protected Integer confirmedRequests;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime eventDate;

    protected Long id;

    protected UserShortDto initiator;

    protected Boolean paid;

    protected String title;

    protected Integer views;

    public EventShortDto(Event event) {
        annotation = event.getAnnotation();
        category = new CategoryDto(event.getCategory());
        confirmedRequests = event.getConfirmedRequests();
        eventDate = event.getEventDate();
        id = event.getId();
        initiator = new UserShortDto(event.getInitiator());
        paid = event.getPaid();
        title = event.getTitle();
        views = event.getViews();
    }
}
