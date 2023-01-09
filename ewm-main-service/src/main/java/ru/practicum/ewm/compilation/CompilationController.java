package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.Dto.CompilationDto;
import ru.practicum.ewm.compilation.Dto.NewCompilationDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class CompilationController {

    private final CompilationService service;

    @GetMapping("/compilations")
    public List<CompilationDto> getAll(@RequestParam(defaultValue = "0") long from,
                                       @RequestParam(defaultValue = "10") int size) {
        return service.getAll(from, size)
                      .stream()
                      .map(CompilationDto::new)
                      .collect(Collectors.toList());
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        return new CompilationDto(service.getById(compId));
    }

    @PostMapping("/admin/compilations")
    public CompilationDto create(@Valid @RequestBody NewCompilationDto dto) {
        return new CompilationDto(service.create(dto.makeEntity(), dto.getEvents()));
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public void delete(@PathVariable Long compId) {
        service.delete(compId);
    }

    @PatchMapping("/admin/compilations/{compId}/events/{eventId}")
    public void addEvent(@PathVariable Long compId,
                         @PathVariable Long eventId) {
        service.includeEvent(compId, eventId, true);
    }

    @DeleteMapping("/admin/compilations/{compId}/events/{eventId}")
    public void removeEvent(@PathVariable Long compId,
                         @PathVariable Long eventId) {
        service.includeEvent(compId, eventId, false);
    }

    @PatchMapping("/admin/compilations/{compId}/pin")
    private void pin(@PathVariable Long compId) {
        service.pin(compId, true);
    }

    @DeleteMapping("/admin/compilations/{compId}/pin")
    private void unPin(@PathVariable Long compId) {
        service.pin(compId, false);
    }
}
