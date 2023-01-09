package ru.practicum.ewm.compilation.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.compilation.Compilation;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class CompilationDto {

    private final Set<EventShortDto> events;

    private final long id;

    private final boolean pinned;

    private final String title;

    public CompilationDto(Compilation compilation) {
        this.id = compilation.getId();
        this.title = compilation.getTitle();
        this.pinned = compilation.getPinned();
        this.events = new HashSet<>();
        compilation.getEvents().forEach(event -> this.events.add(new EventShortDto(event)));
    }
}
