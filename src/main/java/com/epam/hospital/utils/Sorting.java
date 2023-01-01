package com.epam.hospital.utils;

import lombok.Getter;

@Getter
public enum Sorting {
    ALPHABETICALLY("last_name", "alphabetically"),
    BY_CATEGORY("category_id", "byCategory"),
    NUMBER_OF_PATIENTS("number_of_patients DESC", "numberOfPatients"),
    BIRTH_DATE("birth_date", "byBirthDate");

    private final String persistenceValue;
    private final String viewValue;


    Sorting(String persistenceValue, String viewValue) {
        this.persistenceValue = persistenceValue;
        this.viewValue = viewValue;
    }
}
