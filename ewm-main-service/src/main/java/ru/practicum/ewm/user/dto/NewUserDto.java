package ru.practicum.ewm.user.dto;

import lombok.Getter;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.common.Dto;
import ru.practicum.ewm.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class NewUserDto implements Dto<User> {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Override
    public User makeEntity() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);

        return user;
    }
}
