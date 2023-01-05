package ru.practicum.ewm.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestDto {

    private String created;

    private long event;

    private long id;

    private long requester;

    private String status;
}
