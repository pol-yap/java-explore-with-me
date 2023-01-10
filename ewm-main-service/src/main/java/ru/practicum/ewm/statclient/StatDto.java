package ru.practicum.ewm.statclient;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StatDto {
    private String app;
    private String ip;
    private String uri;
    private String timestamp;
}