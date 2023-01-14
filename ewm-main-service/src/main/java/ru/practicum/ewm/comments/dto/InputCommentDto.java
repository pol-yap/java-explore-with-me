package ru.practicum.ewm.comments.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class InputCommentDto {

    @NotBlank
    @Size(max = 7000)
    private String text;
}
