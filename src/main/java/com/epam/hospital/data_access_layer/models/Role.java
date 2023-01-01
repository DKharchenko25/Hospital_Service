package com.epam.hospital.data_access_layer.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private long id;
    private String name;

    public Role(long id) {
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
