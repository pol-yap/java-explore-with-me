package ru.practicum.ewmstat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
public class EndpointHitDto {
    private Long id;
    private String app;
    private String ip;
    private String uri;
    private String timestamp;

    @JsonIgnore
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EndpointHitDto(EndpointHit hit) {
        this.id = hit.getId();
        this.app = hit.getApp();
        this.ip = hit.getIp();
        this.uri = hit.getUri();
        this.timestamp = hit.getTimestamp().format(formatter);
    }

    public EndpointHit makeEntity() {
        EndpointHit hit = new EndpointHit();
        hit.setApp(app);
        hit.setIp(ip);
        hit.setUri(uri);
        hit.setTimestamp(LocalDateTime.parse(timestamp, formatter));

        return hit;
    }
}
