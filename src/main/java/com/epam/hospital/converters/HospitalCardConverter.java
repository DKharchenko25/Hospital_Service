package com.epam.hospital.converters;

import com.epam.hospital.dtos.HospitalCardDto;
import com.epam.hospital.models.HospitalCard;

public class HospitalCardConverter {

    public static HospitalCardDto convertHospitalCardToDto(HospitalCard hospitalCard) {
        HospitalCardDto dto = new HospitalCardDto();
        dto.setId(hospitalCard.getId());
        dto.setPatient(hospitalCard.getPatient());
        dto.setDoctor(hospitalCard.getDoctor());
        dto.setPatientId(hospitalCard.getPatientId());
        dto.setDoctorId(hospitalCard.getDoctorId());
        dto.setRecordDate(hospitalCard.getRecordDate());
        dto.setProcedures(hospitalCard.getProcedures());
        dto.setMedications(hospitalCard.getMedications());
        dto.setOperations(hospitalCard.getOperations());
        dto.setDiagnosis(hospitalCard.getDiagnosis());
        return dto;
    }
}
