package com.epam.hospital.converters;

import com.epam.hospital.dtos.PatientDto;
import com.epam.hospital.models.Patient;

public class PatientConverter {

    public static PatientDto convertPatientToDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setUsername(patient.getUsername());
        patientDto.setPassword(patient.getPassword());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setBirthDate(patient.getBirthDate());
        patientDto.setEmail(patient.getEmail());
        patientDto.setPhoneNumber(patient.getPhoneNumber());
        return patientDto;
    }

    public static Patient convertDtoToPatient(PatientDto dto) {
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setUsername(dto.getUsername());
        patient.setPassword(dto.getPassword());
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setBirthDate(dto.getBirthDate());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());
        return patient;
    }
}
