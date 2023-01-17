package ru.practicum.ewm.participation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findByRequesterId(Long userId);

    Optional<ParticipationRequest> findByIdAndRequesterId(Long id, Long userId);

    List<ParticipationRequest> findByEventId(Long id);

    Optional<ParticipationRequest> findByEventIdAndRequesterId(Long eventIdd, Long requesterId);

}
