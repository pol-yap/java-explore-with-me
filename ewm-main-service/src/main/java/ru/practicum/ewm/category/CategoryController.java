package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.Dto.CategoryDto;
import ru.practicum.ewm.category.Dto.NewCategoryDto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") int from,
                                    @RequestParam(defaultValue = "10") int size) {
        final List<CategoryDto> result = new ArrayList<>();
        service.getAll(from, size).forEach(category -> result.add(new CategoryDto(category)));

        return result;
    }

    @GetMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getById(@PathVariable("catId") long categoryId) {
        return new CategoryDto(service.getByIdOrThrow(categoryId));
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
