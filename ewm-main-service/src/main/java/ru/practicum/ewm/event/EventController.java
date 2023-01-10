package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.statclient.StatClient;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class EventController {

    private final EventService service;
    private final StatClient statClient;
    private final HttpServletRequest request;

    @GetMapping("/events/{eventId}")
    public EventFullDto publicGetById(@PathVariable("eventId") long eventId) {
        statClient.hit(request.getRequestURI(), request.getRemoteAddr());

        return new EventFullDto(service.publicGetById(eventId));
    }

    @PostMapping("/users/{userId}/events")
    public EventFullDto create(@PathVariable("userId") long userId,
                               @Valid @RequestBody NewEventDto dto) {

        return new EventFullDto(service.create(
                dto.makeEntity(),
                userId,
                dto.getCategory()));
    }

    @GetMapping("/events")
    public List<EventShortDto> publicSearch(@RequestParam(required = false) String text,
                                            @RequestParam(required = false) Long[] categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false) String rangeStart,
                                            @RequestParam(required = false) String rangeEnd,
                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(defaultValue = "0") Long from,
                                            @RequestParam(defaultValue = "10") Integer size) {
        EventSearchCriteria criteria = EventSearchCriteria.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .build();

        statClient.hit(request.getRequestURI(), request.getRemoteAddr());

        return service.search(criteria, from, size)
                      .stream()
                      .map(EventShortDto::new)
                      .collect(Collectors.toList());
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> adminSearch(@RequestParam(required = false) Long[] users,
                                          @RequestParam(required = false) String[] states,
                                          @RequestParam(required = false) Long[] categories,
                                          @RequestParam(required = false) String rangeStart,
                                          @RequestParam(required = false) String rangeEnd,
                                          @RequestParam(defaultValue = "0") Long from,
                                          @RequestParam(defaultValue = "10") Integer size) {
        EventSearchCriteria criteria = EventSearchCriteria.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .build();

        statClient.hit(request.getRequestURI(), request.getRemoteAddr());

        return service.search(criteria, from, size)
                      .stream()
                      .map(EventFullDto::new)
                      .collect(Collectors.toList());
    }

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> privateGetAllByInitiator(@PathVariable Long userId,
                                                 @RequestParam(defaultValue = "0") Long from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        return service.getAllByInitiator(userId, from, size)
                      .stream()
                      .map(EventShortDto::new)
                      .collect(Collectors.toList());
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto privateGetById(@PathVariable("userId") long userId,
                                       @PathVariable("eventId") long eventId) {
        return new EventFullDto(service.privateGetByIdAndUserId(eventId, userId));
    }

    @PatchMapping("/users/{userId}/events")
    public EventFullDto privateUpdate(@PathVariable("userId") long userId,
                                      @Valid @RequestBody PrivateUpdateEventRequest data) {
        return new EventFullDto(service.privateUpdate(data, userId));
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto privateReject(@PathVariable("userId") long userId,
                                      @PathVariable("eventId") long eventId) {
        return new EventFullDto(service.privateCancel(eventId, userId));
    }

    @PutMapping("/admin/events/{eventId}")
    public EventFullDto adminUpdate(@PathVariable("eventId") long eventId,
                                    @RequestBody AdminUpdateEventRequest data) {
        return new EventFullDto(service.adminUpdate(data, eventId));
    }

    @PatchMapping("/admin/events/{eventId}/publish")
    public EventFullDto adminPublish(@PathVariable("eventId") long eventId) {
        return new EventFullDto(service.adminPublish(eventId));
    }

    @PatchMapping("/admin/events/{eventId}/reject")
    public EventFullDto adminReject(@PathVariable("eventId") long eventId) {
        return new EventFullDto(service.adminCancel(eventId));
    }
}
