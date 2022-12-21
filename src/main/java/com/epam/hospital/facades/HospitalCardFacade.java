package com.epam.hospital.facades;

import com.epam.hospital.dtos.HospitalCardDto;

import java.util.List;

public interface HospitalCardFacade {
    HospitalCardDto getHospitalCardById(Long id);

    List<HospitalCardDto> getAllHospitalCards();

    void addHospitalCard(Long patientId, Long hospitalStaffId);

    void deleteHospitalCardById(Long id);

    void insertRecordToPatientProcedures(Long cardId, String procedure);

    void insertRecordToPatientMedications(Long cardId, String medication);

    void insertRecordToPatientOperations(Long cardId, String operation);

    void insertRecordToPatientDiagnosis(Long cardId, String diagnosis);

    void writeAndSendCardToPatient(Long patientId, String path);
}
