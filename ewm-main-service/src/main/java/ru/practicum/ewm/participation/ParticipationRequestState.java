package ru.practicum.ewm.participation;

import java.util.Optional;

public enum ParticipationRequestState {
    PENDING,
    REJECTED,
    CANCELED,
    CONFIRMED;

    public static Optional<ParticipationRequestState> from(String stringValue) {
        for (ParticipationRequestState state : values()) {
            if (state.name().equalsIgnoreCase(stringValue)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}