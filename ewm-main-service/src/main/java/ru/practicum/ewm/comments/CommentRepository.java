package ru.practicum.ewm.comments;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndAuthorId(Long commentId, Long userId);

    Optional<Comment> findByIdAndEventInitiatorId(Long commentId, Long userId);

    Optional<Comment> findByIdAndEventId(Long commentId, Long eventId);

    List<Comment> findByEventId(Long id, Pageable pageable);

}
