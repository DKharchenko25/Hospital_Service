package com.epam.hospital.dtos;

import com.epam.hospital.models.Category;
import com.epam.hospital.models.Role;
import lombok.Data;

@Data
public class HospitalStaffDto {
    private long id;
    private String username;
    private String password;
    private String roleName;
    private String categoryName;
    private Role role;
    private Category category;
    private String firstName;
    private String lastName;
    private long numberOfPatients;
}
