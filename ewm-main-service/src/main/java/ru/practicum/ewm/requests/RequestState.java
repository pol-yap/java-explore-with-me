package ru.practicum.ewm.requests;

import java.util.Optional;

public enum RequestState {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static Optional<RequestState> from(String stringValue) {
        for (RequestState state : values()) {
            if (state.name().equalsIgnoreCase(stringValue)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}