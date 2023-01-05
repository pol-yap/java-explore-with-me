package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewUserDto {

    private final String name;

    private final String email;
}
