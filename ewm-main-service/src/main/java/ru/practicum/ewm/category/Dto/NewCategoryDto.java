package ru.practicum.ewm.category.Dto;

import lombok.Getter;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.common.Dto;

import javax.validation.constraints.NotBlank;

@Getter
public class NewCategoryDto implements Dto<Category> {

    @NotBlank
    private String name;

    @Override
    public Category makeEntity() {
        Category category = new Category();
        category.setName(this.name);
        return category;
    }
}
