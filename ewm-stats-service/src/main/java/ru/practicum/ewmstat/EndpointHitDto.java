package ru.practicum.ewmstat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class EndpointHitDto {
    private Long id;
    private String app;
    private String ip;
    private String uri;
    private String timestamp;

    public EndpointHitDto(EndpointHit hit) {
        this.id = hit.getId();
        this.app = hit.getApp();
        this.ip = hit.getIp();
        this.uri = hit.getUri();
        this.timestamp = hit.getTimestamp().format(EwmDateTimeFormatter.formatter);
    }

    public EndpointHit makeEntity() {
        EndpointHit hit = new EndpointHit();
        hit.setApp(app);
        hit.setIp(ip);
        hit.setUri(uri);
        hit.setTimestamp(LocalDateTime.parse(timestamp, EwmDateTimeFormatter.formatter));

        return hit;
    }
}
