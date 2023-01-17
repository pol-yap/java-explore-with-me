package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") int from,
                                    @RequestParam(defaultValue = "10") int size) {

        return service.getAll(from, size)
                      .stream()
                      .map(CategoryDto::new)
                      .collect(Collectors.toList());
    }

    @GetMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getById(@PathVariable("catId") long categoryId) {
        return new CategoryDto(service.getById(categoryId));
    }

    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto create(@Valid @RequestBody NewCategoryDto dto) {
        return new CategoryDto(service.create(dto.makeEntity()));
    }

    @PatchMapping("/admin/categories")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto update(@Valid @RequestBody CategoryDto dto) {
        return new CategoryDto(service.update(dto.makeEntity()));
    }

    @DeleteMapping("/admin/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("catId") long categoryId) {
        service.delete(categoryId);
    }
}
