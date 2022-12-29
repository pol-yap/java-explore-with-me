package ru.practicum.ewm.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.compilation.Compilation;
import ru.practicum.ewm.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString

@Entity
@Table(name = "events")
public class Event {

    @Id
    private Long id;

    private String annotation;

    @ManyToOne
    @ToString.Exclude
    private Category category;

    @ManyToMany(mappedBy = "events")
    @ToString.Exclude
    private Set<Compilation> compilations;

    private Integer confirmedRequests;

    private LocalDateTime createdOn;

    private String description;

    private LocalDateTime eventDate;

    @ManyToOne
    @ToString.Exclude
    private User initiator;

    @ManyToOne
    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private EventState state;

    private String title;

    private Integer views;
}
