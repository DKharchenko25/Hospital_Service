package com.epam.hospital.facades.converters;

import com.epam.hospital.data_access_layer.models.Category;
import com.epam.hospital.facades.dtos.CategoryDto;

public class CategoryConverter {

    public static Category convertCategoryDtoToCategory(CategoryDto dto) {
        return new Category(dto.getId(), dto.getName());
    }

    public static CategoryDto convertCategoryToCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
