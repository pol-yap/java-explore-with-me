package ru.practicum.ewm.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.dto.InputCommentDto;
import ru.practicum.ewm.comments.dto.OutputCommentDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@ResponseStatus(HttpStatus.OK)
public class CommentController {

    private final CommentService service;

    @PostMapping("/users/{userId}/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public OutputCommentDto create(@PathVariable Long userId,
                                   @PathVariable Long eventId,
                                   @Valid @RequestBody InputCommentDto dto) {

        return new OutputCommentDto(service.create(userId, eventId, dto.getText()));
    }

    @PutMapping("/users/{userId}/comments/{commentId}")
    public OutputCommentDto update(@PathVariable Long userId,
                                   @PathVariable Long commentId,
                                   @Valid @RequestBody InputCommentDto dto) {
        return new OutputCommentDto(service.update(userId, commentId, dto.getText()));
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public void privateDelete(@PathVariable Long userId,
                              @PathVariable Long commentId) {
        service.delete(userId, commentId);
    }

    @PatchMapping("/users/{userId}/comments/{commentId}/hide")
    public void hideByInitiator(@PathVariable Long userId,
                                @PathVariable Long commentId) {
        service.hideByInitiator(userId, commentId);
    }

    @PatchMapping("/users/{userId}/comments/{commentId}/show")
    public void showByInitiator(@PathVariable Long userId,
                                @PathVariable Long commentId) {
        service.showByInitiator(userId, commentId);
    }

    @PatchMapping("/admin/comments/{commentId}/hide")
    public void hideByAdmin(@PathVariable Long commentId) {
        service.hideByAdmin(commentId);
    }

    @PatchMapping("/admin/comments/{commentId}/show")
    public void showByAdmin(@PathVariable Long commentId) {
        service.showByAdmin(commentId);
    }

    @GetMapping("/events/{eventId}/comments/{commentId}")
    public OutputCommentDto publicGetById(@PathVariable Long eventId,
                                          @PathVariable Long commentId) {
        return new OutputCommentDto(service.getByIdAndEventId(commentId, eventId));
    }

    @GetMapping("/events/{eventId}/comments")
    public List<OutputCommentDto> publicGetByEvent(@PathVariable Long eventId,
                                                   @RequestParam(defaultValue = "0") Long from,
                                                   @RequestParam(defaultValue = "10") Integer size) {

        return service.getAllByEventId(eventId, from, size)
                      .stream()
                      .map(OutputCommentDto::new)
                      .collect(Collectors.toList());
    }
}
