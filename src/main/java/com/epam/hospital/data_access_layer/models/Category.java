package com.epam.hospital.data_access_layer.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private long id;
    private String name;

    public Category(long id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
