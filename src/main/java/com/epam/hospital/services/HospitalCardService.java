package com.epam.hospital.services;

import com.epam.hospital.models.HospitalCard;

import java.util.List;

public interface HospitalCardService {

    void addHospitalCard(Long patientId, Long hospitalStaffId);

    HospitalCard getHospitalCardById(Long id);

    List<HospitalCard> getAllHospitalCards();

    List<HospitalCard> getAllPatientHospitalCardsById(Long id);

    void deleteHospitalCardById(Long id);

    void insertRecordToPatientProcedures(Long cardId, String procedure);

    void insertRecordToPatientMedications(Long cardId, String medication);

    void insertRecordToPatientOperations(Long cardId, String operation);

    void insertRecordToPatientDiagnosis(Long cardId, String diagnosis);

    void writeAndSendCardToPatient(Long patientId, String path);
}
