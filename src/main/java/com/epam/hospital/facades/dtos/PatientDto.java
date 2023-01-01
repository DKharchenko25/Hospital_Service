package com.epam.hospital.facades.dtos;

import lombok.Data;

@Data
public class PatientDto {
    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String phoneNumber;
}
