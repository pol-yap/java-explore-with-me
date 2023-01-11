package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.TrimRequest;
import ru.practicum.ewm.common.errors.NotFoundException;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompilationService {

    private final CompilationRepository repository;
    private final EventService eventService;

    public Compilation create(Compilation compilation, Set<Long> eventIds) {
        Set<Event> events = new HashSet<>();
        eventIds.forEach(id -> events.add(eventService.adminGetById(id)));
        compilation.setEvents(events);
        repository.saveAndFlush(compilation);
        log.info("Compilation created: {}", compilation);

        return compilation;
    }

    public Compilation getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new NotFoundException(id, "Compilation"));
    }

    public List<Compilation> getAll(Long from, Integer size) {
        Pageable pageable = new TrimRequest(from, size, Sort.by("id").ascending());
        return repository.findAll(pageable).getContent();
    }

    public void pin(Long id, Boolean pinned) {
        Compilation compilation = this.getById(id);
        compilation.setPinned(pinned);
        repository.saveAndFlush(compilation);
        log.info("Compilation pinned(unpinned): {}", compilation);
    }

    public void includeEvent(Long compilationId, Long eventId, boolean included) {
        Compilation compilation = this.getById(compilationId);
        Event event = eventService.adminGetById(eventId);
        Set<Event> events = compilation.getEvents();
        if (included) {
            events.add(event);
        } else {
            events.remove(event);
        }
        compilation.setEvents(events);
        repository.saveAndFlush(compilation);
        log.info("Event {} {} compilation {}",
                event,
                (included) ? "added to" : "removed from",
                compilation);
    }

    public void delete(Long id) {
        repository.deleteById(id);
        log.info("Compilation with id {} is deleted", id);
    }
}
