package ru.practicum.ewm.event;

import java.util.Optional;

public enum EventState {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static Optional<EventState> from(String stringValue) {
        for (EventState state : values()) {
            if (state.name().equalsIgnoreCase(stringValue)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
