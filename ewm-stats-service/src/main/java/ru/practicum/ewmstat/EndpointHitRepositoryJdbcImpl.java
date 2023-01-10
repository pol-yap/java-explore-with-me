package ru.practicum.ewmstat;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class EndpointHitRepositoryJdbcImpl implements EndpointHitRepository {
    private final NamedParameterJdbcOperations namedJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_ALL =
            "SELECT Count(id) AS hits, uri, app FROM endpoint_hit " +
            "WHERE hit_timestamp Between :start_date and :end_date AND uri IN (:uris) " +
            "GROUP BY uri, app";

    private static final String SQL_SELECT_UNIQUE =
            "SELECT Count(ip) AS hits, uri, app FROM " +
                    "(SELECT DISTINCT ip, uri, app FROM endpoint_hit " +
                    "WHERE hit_timestamp Between :start_date and :end_date AND uri IN (:uris)) AS u " +
                    "GROUP BY uri, app";

    @Override
    public EndpointHit hit(final EndpointHit hit) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("endpoint_hit")
                .usingGeneratedKeyColumns("id");
        Long id = simpleJdbcInsert.executeAndReturnKey(this.hitToMap(hit)).longValue();
        hit.setId(id);

        return hit;
    }

    @Override
    public List<ViewStats> getStats(final LocalDateTime start,
                                    final LocalDateTime end,
                                    final List<String> uris,
                                    final Boolean unique) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("start_date", start);
        parameters.addValue("end_date", end);
        parameters.addValue("uris", uris);

        String sql = (unique) ? SQL_SELECT_UNIQUE : SQL_SELECT_ALL;

        return namedJdbcTemplate.query(sql, parameters,
                (rs, rowNum) -> ViewStats.builder()
                                         .hits(rs.getLong("hits"))
                                         .uri(rs.getString("uri"))
                                         .app(rs.getString("app"))
                                         .build());
    }

    private Map<String, Object> hitToMap(EndpointHit hit) {
        return Map.of(
                "app", hit.getApp(),
                "uri", hit.getUri(),
                "ip", hit.getIp(),
                "hit_timestamp", hit.getTimestamp());
    }
}
