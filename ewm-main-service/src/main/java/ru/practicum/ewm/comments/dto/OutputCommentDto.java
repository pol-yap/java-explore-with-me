package ru.practicum.ewm.comments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.ewm.comments.Comment;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

public class OutputCommentDto {

    private Long id;

    private String text;

    private EventShortDto event;

    private UserShortDto author;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedOn;

    private Boolean modified;

    private String state;

    public OutputCommentDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.event = new EventShortDto(comment.getEvent());
        this.author = new UserShortDto(comment.getAuthor());
        this.createdOn = comment.getCreatedOn();
        this.lastModifiedOn = comment.getLastModifiedOn();
        this.modified = comment.getModified();
        this.state = comment.getState().name();
    }
}
