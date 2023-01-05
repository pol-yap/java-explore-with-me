package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.user.User;

@Getter
@AllArgsConstructor
public class UserDto {

    private final long id;

    private final String name;

    private final String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
