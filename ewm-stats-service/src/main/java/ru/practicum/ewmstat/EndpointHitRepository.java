package ru.practicum.ewmstat;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository {

    EndpointHit hit(EndpointHit hit);

    List<ViewStats> getStats(LocalDateTime start,
                             LocalDateTime end,
                             List<String> uris,
                             Boolean unique);
}
