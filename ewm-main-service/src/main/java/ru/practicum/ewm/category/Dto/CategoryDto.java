package ru.practicum.ewm.category.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.common.Dto;

@Getter
@AllArgsConstructor
public class CategoryDto implements Dto<Category> {

    private final long id;

    private final String name;

    public CategoryDto(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    @Override
    public Category makeEntity() {
        return new Category(id, name);
    }
}
