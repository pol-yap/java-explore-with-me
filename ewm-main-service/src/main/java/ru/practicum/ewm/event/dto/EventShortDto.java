package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.category.Dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    protected String annotation;

    protected CategoryDto category;

    protected int confirmedRequests;

    protected String eventDate;

    protected long id;

    protected UserShortDto initiator;

    protected boolean paid;

    protected String title;

    protected int views;
}
