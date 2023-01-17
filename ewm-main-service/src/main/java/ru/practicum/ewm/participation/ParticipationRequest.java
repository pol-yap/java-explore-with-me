package ru.practicum.ewm.participation;

import com.querydsl.core.annotations.QueryExclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString

@Entity
@QueryExclude
@Table(name = "requests")
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User requester;

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    private ParticipationRequestState state = ParticipationRequestState.PENDING;
}
