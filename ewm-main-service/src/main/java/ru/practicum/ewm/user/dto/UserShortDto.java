package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.user.User;

@Getter
@AllArgsConstructor
public class UserShortDto {

    private final long id;

    private final String name;

    public UserShortDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
