package ru.practicum.ewm.participation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.participation.dto.ParticipationRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class ParticipationRequestController {

    private final ParticipationRequestService service;

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getAllOfUser(@PathVariable Long userId) {
        return service.getAllOfUser(userId)
                      .stream()
                      .map(ParticipationRequestDto::new)
                      .collect(Collectors.toList());
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getAllOfEvent(@PathVariable Long userId,
                                                       @PathVariable Long eventId) {
        return service.getAllOfEvent(eventId)
                      .stream()
                      .map(ParticipationRequestDto::new)
                      .collect(Collectors.toList());
    }

    @PostMapping("/users/{userId}/requests")
    public ParticipationRequestDto create(@PathVariable Long userId,
                                          @RequestParam Long eventId) {
        return new ParticipationRequestDto(service.create(eventId, userId));
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelByRequester(@PathVariable Long userId,
                                                     @PathVariable Long requestId) {
        return new ParticipationRequestDto(service.cancel(requestId));
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmByInitiator(@PathVariable Long userId,
                                                      @PathVariable Long eventId,
                                                      @PathVariable(name = "reqId") Long requestId) {
        return new ParticipationRequestDto(service.confirm(requestId));
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectByInitiator(@PathVariable Long userId,
                                                     @PathVariable Long eventId,
                                                     @PathVariable(name = "reqId") Long requestId) {
        return new ParticipationRequestDto(service.reject(requestId));
    }
}
