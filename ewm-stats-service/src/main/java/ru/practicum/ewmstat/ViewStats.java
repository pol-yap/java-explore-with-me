package ru.practicum.ewmstat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
