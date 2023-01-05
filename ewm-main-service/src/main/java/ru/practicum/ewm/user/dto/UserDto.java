package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UserDto {

    @NotNull(message = "User id shouldn't be null")
    private final long id;

    @NotBlank(message = "User name shouldn't be empty")
    private final String name;

    @Email(message = "User email should be valid e-mail address")
    private final String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
