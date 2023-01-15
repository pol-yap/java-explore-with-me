package ru.practicum.ewm.comments.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class InputCommentDto {

    @NotBlank
    @Size(max = 7000)
    private String text;
}
