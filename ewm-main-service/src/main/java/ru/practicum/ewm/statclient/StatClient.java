package ru.practicum.ewm.statclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StatClient {

    private final RestTemplate restTemplate;

    @Value("${ewm.stat-service-url}")
    private String statServiceBaseUrl;

    @Autowired
    public StatClient() {
        this.restTemplate = new RestTemplate();
    }

    public void hit(String uri, String ip) {
        StatDto dto = new StatDto(
                "ewm-main-service",
                ip,
                uri,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        HttpEntity<StatDto> request = new HttpEntity<>(dto);
        String url = statServiceBaseUrl + "/hit";
        restTemplate.exchange(url, HttpMethod.POST, request, StatDto.class);
    }
}
