package ru.practicum.ewm.requests;

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
@Table(name = "requests")
public class Request {

    @Id
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User requester;

    @Column(nullable = false)
    private LocalDateTime created;
}
