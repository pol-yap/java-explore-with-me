package ru.practicum.ewm.category.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.common.Dto;

@Getter
@AllArgsConstructor
public class NewCategoryDto implements Dto<Category> {
    private final String name;

    @Override
    public Category makeEntity() {
        Category category = new Category();
        category.setName(this.name);
        return category;
    }
}
