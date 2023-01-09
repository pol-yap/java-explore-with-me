package ru.practicum.ewm.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

@Builder
@Getter
public class EventSearchCriteria {

    private String text;

    private Long[] categories;

    private Long[] users;

    private String[] states;

    private Boolean paid;

    private String rangeStart;

    private String rangeEnd;

    private Boolean onlyAvailable;

    private String sort;
}
