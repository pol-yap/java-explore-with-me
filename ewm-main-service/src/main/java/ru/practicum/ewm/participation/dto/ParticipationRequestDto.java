package ru.practicum.ewm.participation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.participation.ParticipationRequest;
import ru.practicum.ewm.participation.ParticipationRequestState;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ParticipationRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    private Long event;

    private Long id;

    private Long requester;

    private ParticipationRequestState status;

    public ParticipationRequestDto(ParticipationRequest entity) {
        created = entity.getCreated();
        event = entity.getEvent().getId();
        id = entity.getId();
        requester = entity.getRequester().getId();
        status = entity.getState();
    }
}
