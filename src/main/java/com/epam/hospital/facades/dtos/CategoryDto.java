package com.epam.hospital.facades.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;

    public CategoryDto(Long id) {
        this.id = id;
    }

    public CategoryDto(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
