package ru.practicum.ewm.event;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class EventSearchCriteria {

    private String text;

    private List<Long> categories;

    private List<Long>  users;

    private List<String> states;

    private Boolean paid;

    private String rangeStart;

    private String rangeEnd;

    private Boolean onlyAvailable;

    private String sort;
}
