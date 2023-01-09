package ru.practicum.ewm.event;

import lombok.*;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.compilation.Compilation;
import ru.practicum.ewm.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String annotation;

    @ManyToOne
    @ToString.Exclude
    private Category category;

    @ManyToMany(mappedBy = "events")
    @ToString.Exclude
    private Set<Compilation> compilations;

    @Builder.Default
    private Integer confirmedRequests = 0;

    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(length = 7000)
    private String description;

    private LocalDateTime eventDate;

    @ManyToOne
    @ToString.Exclude
    private User initiator;

    private Float latitude;

    private Float longitude;

    private Boolean paid;

    private Integer participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    @Builder.Default
    private EventState state = EventState.PENDING;

    @Column(length = 120)
    private String title;

    private Integer views;

    @Builder.Default
    private Boolean available = true;

    @Override
    public boolean equals(Object e) {
        if (!e.getClass().equals(Event.class)) {
            return false;
        }

       return ((Event) e).getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
