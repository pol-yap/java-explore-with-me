package ru.practicum.ewm.compilation.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class NewCompilationDto {

    private final Collection<Integer> events;

    private final boolean pinned;

    private final String title;
}
