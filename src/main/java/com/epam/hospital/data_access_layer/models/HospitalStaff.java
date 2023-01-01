package com.epam.hospital.data_access_layer.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HospitalStaff {
    private long id;
    private String username;
    private String password;
    private Role role;
    private Category category;
    private String firstName;
    private String lastName;
    private long numberOfPatients;

    public HospitalStaff(long id) {
        this.id = id;
    }
}
