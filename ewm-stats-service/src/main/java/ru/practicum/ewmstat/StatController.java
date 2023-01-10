package ru.practicum.ewmstat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseStatus(HttpStatus.OK)
@RequiredArgsConstructor
public class StatController {

    private final StatService service;

    @PostMapping("/hit")
    public void hit(@RequestBody EndpointHitDto dto) {
        service.hit(dto.makeEntity());
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam String[] uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {

        return service.getStats(start, end, uris, unique);
    }
}
