package com.epam.hospital.repositories;

import lombok.Getter;

@Getter
public enum Sorting {
    ALPHABETICALLY("last_name"),
    BY_CATEGORY("category_id"),
    NUMBER_OF_PATIENTS("number_of_patients DESC"),
    BIRTH_DATE("birth_date");

    private final String value;

    Sorting(String value) {
        this.value = value;
    }
}
