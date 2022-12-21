package com.epam.hospital.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HospitalStaff {
    private long id;
    private String username;
    private String password;
    private Role role;
    private Category category;
    private String firstName;
    private String lastName;
    private long numberOfPatients;
    private String categoryName;
    private String roleName;
    private long categoryId;
    private long roleId;
}
