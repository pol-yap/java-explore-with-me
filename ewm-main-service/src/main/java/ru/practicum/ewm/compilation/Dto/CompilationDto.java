package ru.practicum.ewm.compilation.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.Event;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class CompilationDto {

    private Collection<Event> events;

    private long id;

    private boolean pinned;

    private String title;
}
