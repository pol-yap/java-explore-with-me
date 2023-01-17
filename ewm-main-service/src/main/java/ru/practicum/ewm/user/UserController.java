package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.dto.UserDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/admin/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll(@RequestParam(defaultValue = "0") long from,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam List<Long> ids) {
        final List<UserDto> result = new ArrayList<>();
        service.getAll(ids, from, size).forEach(user -> result.add(new UserDto(user)));

        return result;
    }

    @PostMapping("/admin/users")
    @ResponseStatus(HttpStatus.OK)
    public UserDto create(@Valid @RequestBody NewUserDto dto) {
        return new UserDto(service.create(dto.makeEntity()));
    }

    @DeleteMapping("/admin/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("userId") long userId) {
        service.delete(userId);
    }
}
