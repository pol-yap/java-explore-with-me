package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.common.TrimRequest;

import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;

    public User create(User user) {
        repository.save(user);
        log.info("Created user: {}", user);

        return user;
    }

    public Collection<User> getAll(Long[] ids, int from, int size) {
        Pageable pageable = new TrimRequest(from, size, Sort.by("id").ascending());

        return repository.findByIdInOrderByIdAsc(Arrays.asList(ids), pageable);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}