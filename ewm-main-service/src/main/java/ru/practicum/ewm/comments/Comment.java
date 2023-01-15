package ru.practicum.ewm.comments;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 7000)
    private String text;

    @ManyToOne
    @ToString.Exclude
    private Event event;

    @ManyToOne
    @ToString.Exclude
    private User author;

    private LocalDateTime createdOn = LocalDateTime.now();

    private LocalDateTime lastModifiedOn;

    private Boolean modified = false;

    private CommentState state = CommentState.PUBLISHED;

    private Boolean isEvidence;
}
