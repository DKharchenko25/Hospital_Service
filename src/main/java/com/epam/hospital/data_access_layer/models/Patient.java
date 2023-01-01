package com.epam.hospital.data_access_layer.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Patient {
    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String phoneNumber;

    public Patient(long id) {
        this.id = id;
    }
}
