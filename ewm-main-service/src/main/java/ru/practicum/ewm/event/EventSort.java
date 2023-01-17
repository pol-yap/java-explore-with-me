package ru.practicum.ewm.event;

public enum EventSort {
    EVENT_DATE,
    VIEWS,
    DEFAULT;

    public static EventSort from(String stringValue) {
        for (EventSort sort : values()) {
            if (sort.name().equalsIgnoreCase(stringValue)) {
                return sort;
            }
        }
        return DEFAULT;
    }
}
