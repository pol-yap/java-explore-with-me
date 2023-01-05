package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class LocationDto {

    @NotNull(message = "Event location latitude shouldn't be null")
    private Float lat;

    @NotNull(message = "Event location longitude shouldn't be null")
    private Float lon;
}
