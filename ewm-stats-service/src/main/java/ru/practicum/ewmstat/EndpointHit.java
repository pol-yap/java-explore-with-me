package ru.practicum.ewmstat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EndpointHit {

    private Long id;

    private String app;

    private String uri;

    private String ip;

    private LocalDateTime timestamp = LocalDateTime.now();
}
