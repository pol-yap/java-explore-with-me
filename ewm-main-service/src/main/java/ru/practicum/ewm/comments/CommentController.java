package ru.practicum.ewm.comments;
//test
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.dto.InputCommentDto;
import ru.practicum.ewm.comments.dto.OutputCommentDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@ResponseStatus(HttpStatus.OK)
public class CommentController {

    @PostMapping("/users/{userId}/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public OutputCommentDto create(@PathVariable Long userId,
                                   @PathVariable Long eventId,
                                   @Valid @RequestBody InputCommentDto dto) {
        return null;
    }

    @PatchMapping("/users/{userId}/comments/{commentId}")
    public OutputCommentDto update(@PathVariable Long userId,
                                   @PathVariable Long commentId,
                                   @Valid @RequestBody InputCommentDto dto) {
        return null;
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public void privateDelete(@PathVariable Long userId,
                              @PathVariable Long commentId) {

    }

    @PatchMapping("/users/{userId}/comments/{commentId}/hide")
    public void hideByInitiator(@PathVariable Long userId,
                                @PathVariable Long commentId) {

    }

    @PatchMapping("/admin/comments/{commentId}/hide")
    public void hideByAdmin(@PathVariable Long commentId) {

    }

    @GetMapping("/events/{eventId}/comments/{commentId}")
    public OutputCommentDto publicGetById(@PathVariable Long eventId,
                                          @PathVariable Long commentId) {
        return null;
    }

    @GetMapping("/events/{eventId}/comments")
    public List<OutputCommentDto> publicGetByEvent(@PathVariable Long eventId) {

        return null;
    }


}
