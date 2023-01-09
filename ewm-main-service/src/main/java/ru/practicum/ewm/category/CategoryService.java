package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.common.TrimRequest;
import ru.practicum.ewm.common.errors.NotFoundException;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public Category create(Category category) {
        repository.save(category);
        log.info("Created category: {}", category);

        return category;
    }

    public Category getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id, "Category"));
    }

    public Collection<Category> getAll(int from, int size) {
        Pageable pageable = new TrimRequest(from, size, Sort.by("id").ascending());

        return repository.findAll(pageable).getContent();
    }

    public Category update(Category category) {
        Long id = category.getId();
        if (!repository.existsById(id)) {
            throw new NotFoundException(id, "Category");
        }
        repository.saveAndFlush(category);
        log.info("Updated category: {}", category);

        return category;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
