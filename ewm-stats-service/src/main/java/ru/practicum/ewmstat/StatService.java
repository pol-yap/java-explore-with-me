package ru.practicum.ewmstat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {

    private final EndpointHitRepository repository;

    public void hit(EndpointHit hit) {
        repository.hit(hit);
    }

    public List<ViewStats> getStats(String start,
                                    String end,
                                    List<String> uris,
                                    Boolean unique) {
        return repository.getStats(
                LocalDateTime.parse(start, EwmDateTimeFormatter.formatter),
                LocalDateTime.parse(end, EwmDateTimeFormatter.formatter),
                uris,
                unique);
    }
}
