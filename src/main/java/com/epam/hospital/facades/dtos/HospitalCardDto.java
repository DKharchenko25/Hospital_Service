package com.epam.hospital.facades.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class HospitalCardDto {
    private long id;
    private PatientDto patient;
    private HospitalStaffDto doctor;
    private Date recordDate;
    private String procedures;
    private String medications;
    private String operations;
    private String diagnosis;
}
