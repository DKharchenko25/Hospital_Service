package com.epam.hospital.facades.dtos;

import lombok.Data;

@Data
public class HospitalStaffDto {
    private long id;
    private String username;
    private String password;
    private RoleDto role;
    private CategoryDto category;
    private String firstName;
    private String lastName;
    private long numberOfPatients;
}
