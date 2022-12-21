package com.epam.hospital.dtos;

import com.epam.hospital.models.HospitalStaff;
import com.epam.hospital.models.Patient;
import lombok.Data;

import java.sql.Date;

@Data
public class HospitalCardDto {
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
