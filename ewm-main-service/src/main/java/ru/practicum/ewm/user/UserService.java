package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.TrimRequest;
import ru.practicum.ewm.common.errors.NotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository repository;

    public User create(User user) {
        repository.save(user);
        log.info("Created user: {}", user);

        return user;
    }

    public Collection<User> getAll(List<Long> ids, long from, int size) {
        Pageable pageable = new TrimRequest(from, size, Sort.by("id").ascending());

        return repository.findByIdInOrderByIdAsc(ids, pageable);
    }

    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id, "User"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
