package com.epam.hospital.facades.converters;

import com.epam.hospital.data_access_layer.models.HospitalCard;
import com.epam.hospital.facades.dtos.HospitalCardDto;

public class HospitalCardConverter {

    public static HospitalCardDto convertHospitalCardToDto(HospitalCard hospitalCard) {
        HospitalCardDto dto = new HospitalCardDto();
        dto.setId(hospitalCard.getId());
        dto.setPatient(PatientConverter.convertPatientToDto(hospitalCard.getPatient()));
        dto.setDoctor(HospitalStaffConverter.convertHospitalStaffToDto(hospitalCard.getDoctor()));
        dto.setRecordDate(hospitalCard.getRecordDate());
        dto.setProcedures(hospitalCard.getProcedures());
        dto.setMedications(hospitalCard.getMedications());
        dto.setOperations(hospitalCard.getOperations());
        dto.setDiagnosis(hospitalCard.getDiagnosis());
        return dto;
    }
}
