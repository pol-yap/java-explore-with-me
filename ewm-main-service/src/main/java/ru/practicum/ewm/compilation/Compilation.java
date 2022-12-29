package ru.practicum.ewm.compilation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.event.Event;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString

@Entity
@Table(name = "compilations")
public class Compilation {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "events_compilations",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    @ToString.Exclude
    private Set<Event> events;

    private Boolean pinned = false;
}