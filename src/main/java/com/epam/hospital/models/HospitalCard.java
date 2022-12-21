package com.epam.hospital.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class HospitalCard {
    private long id;
    private Patient patient;
    private HospitalStaff doctor;
    private long patientId;
    private long doctorId;
    private Date recordDate;
    private String procedures;
    private String medications;
    private String operations;
    private String diagnosis;
}
