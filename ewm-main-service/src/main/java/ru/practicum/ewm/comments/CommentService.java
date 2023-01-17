package ru.practicum.ewm.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.common.TrimRequest;
import ru.practicum.ewm.common.errors.ForbiddenException;
import ru.practicum.ewm.common.errors.NotFoundException;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.participation.ParticipationRequest;
import ru.practicum.ewm.participation.ParticipationRequestService;
import ru.practicum.ewm.participation.ParticipationRequestState;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private final CommentRepository repository;

    private final EventService eventService;

    private final UserService userService;

    private final ParticipationRequestService participationRequestService;

    public Comment create(Long userId, Long eventId, String text) {
        Event event = eventService.publicGetById(eventId);
        User user = userService.getById(userId);

        if (event.getState() != EventState.PUBLISHED) {
            throw new ForbiddenException("Only published events can be commented");
        }

        Comment comment = new Comment(text, event, user);
        Optional<ParticipationRequest> request = participationRequestService.getByEventIdAndRequesterId(
                eventId,
                userId);
        comment.setIsEvidence(
                request.isPresent() && request.get().getState() == ParticipationRequestState.CONFIRMED);
        repository.save(comment);
        log.info("Comment created: {}", comment);

        return comment;
    }

    public Comment update(Long userId, Long commentId, String text) {
        Comment comment = this.getByIdAndAuthorId(commentId, userId);
        if (comment.getState() != CommentState.PUBLISHED) {
            throw new ForbiddenException("Only published comment can be modified");
        }
        this.modifyText(comment, text);
        repository.save(comment);
        log.info("Comment modified: {}",comment);

        return comment;
    }

    public void delete(Long userId, Long commentId) {
        Comment comment = this.getByIdAndAuthorId(commentId, userId);
        this.modifyText(comment, "");
        comment.setState(CommentState.DELETED);
        repository.save(comment);
        log.info("Comment with id {} is deleted", commentId);
    }

    public Comment getByIdAndEventId(Long commentId, Long eventId) {
        return repository.findByIdAndEventId(commentId, eventId)
                .orElseThrow(() -> new NotFoundException(commentId, "Comment"));
    }

    public List<Comment> getAllByEventId(Long eventId, Long from, Integer size) {
        TrimRequest pageable = new TrimRequest(from, size, Sort.by("createdOn").descending());

        return repository.findByEventId(eventId, pageable);
    }

    public void hideByInitiator(Long userId, Long commentId) {
        Comment comment = this.getByIdAndEventInitiatorId(commentId, userId);
        if (comment.getState() != CommentState.PUBLISHED) {
            throw new ForbiddenException("Only published comment can be hidden");
        }
        comment.setState(CommentState.HIDDEN_BY_EVENT_INITIATOR);
        repository.save(comment);
        log.info("Comment {} is hidden by event initiator", comment);
    }

    public void showByInitiator(Long userId, Long commentId) {
        Comment comment = this.getByIdAndEventInitiatorId(commentId, userId);
        if (comment.getState() != CommentState.HIDDEN_BY_EVENT_INITIATOR) {
            throw new ForbiddenException("Only hidden by event initiator comment can be showed by him");
        }
        comment.setState(CommentState.PUBLISHED);
        repository.save(comment);
        log.info("Comment {} is unhidden by event initiator", comment);
    }

    public void hideByAdmin(Long commentId) {
        Comment comment = this.getById(commentId);
        if (comment.getState() != CommentState.PUBLISHED) {
            throw new ForbiddenException("Only published comment can be hidden");
        }
        comment.setState(CommentState.HIDDEN_BY_ADMIN);
        repository.save(comment);
        log.info("Comment {} is hidden by administrator", comment);
    }

    public void showByAdmin(Long commentId) {
        Comment comment = this.getById(commentId);
        if (comment.getState() == CommentState.DELETED) {
            throw new ForbiddenException("Deleted comment can't be showed");
        }
        if (comment.getState() == CommentState.PUBLISHED) {
            throw new ForbiddenException("Comment is already visible");
        }
        comment.setState(CommentState.PUBLISHED);
        repository.save(comment);
        log.info("Comment {} is unhidden by administrator", comment);
    }

    private Comment getByIdAndAuthorId(Long commentId, Long authorId) {
        return repository.findByIdAndAuthorId(commentId, authorId).orElseThrow(
                () -> new NotFoundException(commentId, "Comment")
        );
    }

    private Comment getByIdAndEventInitiatorId(Long commentId, Long userId) {
        return repository.findByIdAndEventInitiatorId(commentId, userId)
                .orElseThrow(() -> new NotFoundException(commentId, "Comment"));
    }

    private Comment getById(Long commentId) {
        return repository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(commentId, "Comment"));
    }

    private void modifyText(Comment comment, String text) {
        comment.setText(text);
        comment.setIsModified(true);
        comment.setLastModifiedOn(LocalDateTime.now());
    }
}
