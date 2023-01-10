package ru.practicum.ewm.participation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.common.errors.DataCheckException;
import ru.practicum.ewm.common.errors.NotFoundException;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipationRequestService {

    private final ParticipationRequestRepository repository;
    private final EventService eventService;
    private final UserService userService;

    public ParticipationRequest create(Long eventId, Long userId) {
        Event event = eventService.publicGetById(eventId);
        User participant = userService.getById(userId);

        if (event.getInitiator().getId().equals(userId)) {
            throw new DataCheckException(String.format(
                    "Initiator %d can't participate in own event %d",
                    userId,
                    event.getId()));
        }

        if (event.getParticipantLimit() != null && event.getConfirmedRequests() != null) {
            if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
                throw new DataCheckException(String.format(
                        "Limit of participants (%d) for event (%d) is reached",
                        event.getParticipantLimit(),
                        event.getId()));
            }
        }

        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(participant);
        request.setEvent(event);

        request.setState(
                event.getRequestModeration() ?
                        ParticipationRequestState.PENDING :
                        ParticipationRequestState.CONFIRMED);

        repository.saveAndFlush(request);
        log.info("Participation request created: {}", request);

        return request;
    }

    public List<ParticipationRequest> getAllOfUser(Long userId) {
        return repository.findByRequesterId(userId);
    }

    public List<ParticipationRequest> getAllOfEvent(Long eventId) {
        return repository.findByEventId(eventId);
    }

    public ParticipationRequest cancel(Long requestId) {
        ParticipationRequest request = this.setState(requestId, ParticipationRequestState.CANCELED);
        log.info("Participation request {} is canceled by requester", requestId);

        return request;
    }

    public ParticipationRequest confirm(Long requestId) {
        ParticipationRequest request = this.setState(requestId, ParticipationRequestState.CONFIRMED);
        eventService.confirmRequest(request.getEvent().getId());
        log.info("Participation request {} is confirmed by initiator", requestId);

        return request;
    }

    public ParticipationRequest reject(Long requestId) {
        ParticipationRequest request = this.setState(requestId, ParticipationRequestState.REJECTED);
        log.info("Participation request {} is rejected by initiator", requestId);

        return request;
    }

    private ParticipationRequest setState(Long requestId, ParticipationRequestState state) {
        ParticipationRequest request = repository.findById(requestId)
                                                 .orElseThrow(() -> new NotFoundException(requestId, "Participation request"));
        request.setState(state);
        return repository.saveAndFlush(request);
    }
}
