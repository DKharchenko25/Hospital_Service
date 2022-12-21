package com.epam.hospital.services;

import com.epam.hospital.models.HospitalCard;
import com.epam.hospital.models.Patient;
import com.epam.hospital.repositories.HospitalCardRepository;
import lombok.NonNull;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class HospitalCardServiceImpl implements HospitalCardService {

    private final HospitalCardRepository hospitalCardRepository;

    private final PatientService patientService;

    private final HospitalStaffService hospitalStaffService;

    private final EmailService emailService;

    private final HospitalCardWriter hospitalCardWriter;

    public HospitalCardServiceImpl(HospitalCardRepository hospitalCardRepository, PatientService patientService,
                                   HospitalStaffService hospitalStaffService, EmailService emailService, HospitalCardWriter hospitalCardWriter) {
        this.hospitalCardRepository = hospitalCardRepository;
        this.patientService = patientService;
        this.hospitalStaffService = hospitalStaffService;
        this.emailService = emailService;
        this.hospitalCardWriter = hospitalCardWriter;
    }

    @Override
    public void addHospitalCard(@NonNull Long patientId, @NonNull Long hospitalStaffId) {
        HospitalCard hospitalCard = new HospitalCard();
        hospitalCard.setPatient(patientService.getPatientById(patientId));
        hospitalCard.setDoctor(hospitalStaffService.getHospitalStaffById(hospitalStaffId));
        hospitalCard.setRecordDate(Date.valueOf(LocalDate.now()));
        hospitalCardRepository.addHospitalCard(hospitalCard);
    }

    @Override
    public HospitalCard getHospitalCardById(@NonNull Long id) {
        if (hospitalCardRepository.getHospitalCardById(id).isPresent()) {
            return hospitalCardRepository.getHospitalCardById(id).get();
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    private String getExceptionMessage(Long id) {
        return String.format("Hospital card with ID # %d is not found", id);
    }

    @Override
    public List<HospitalCard> getAllHospitalCards() {
        return hospitalCardRepository.getAllHospitalCards();
    }

    @Override
    public List<HospitalCard> getAllPatientHospitalCardsById(@NonNull Long id) {
        return hospitalCardRepository.getAllPatientHospitalCardsById(patientService.getPatientById(id).getId());
    }

    @Override
    public void deleteHospitalCardById(@NonNull Long id) {
        if (hospitalCardRepository.getHospitalCardById(id).isPresent()) {
            hospitalCardRepository.deleteHospitalCardById(id);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id));
        }

    }

    @Override
    public void insertRecordToPatientProcedures(@NonNull Long cardId, @NonNull String procedure) {
        if (hospitalCardRepository.getHospitalCardById(cardId).isPresent()) {
            hospitalCardRepository.updateProceduresById(cardId, procedure);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(cardId));
        }
    }

    @Override
    public void insertRecordToPatientMedications(@NonNull Long cardId, @NonNull String medication) {
        if (hospitalCardRepository.getHospitalCardById(cardId).isPresent()) {
            hospitalCardRepository.updateMedicationsById(cardId, medication);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(cardId));
        }
    }

    @Override
    public void insertRecordToPatientOperations(@NonNull Long cardId, @NonNull String operation) {
        if (hospitalCardRepository.getHospitalCardById(cardId).isPresent()) {
            hospitalCardRepository.updateOperationsById(cardId, operation);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(cardId));
        }
    }

    @Override
    public void insertRecordToPatientDiagnosis(@NonNull Long cardId, @NonNull String diagnosis) {
        if (hospitalCardRepository.getHospitalCardById(cardId).isPresent()) {
            hospitalCardRepository.updateDiagnosisById(cardId, diagnosis);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(cardId));
        }
    }

    @Override
    public void writeAndSendCardToPatient(@NonNull Long patientId, @NonNull String path) {
        Patient patient = patientService.getPatientById(patientId);
        hospitalCardWriter.writeHospitalCard(patient, getAllPatientHospitalCardsById(patientId), path);
        emailService.sendHospitalCard(patient, path);
    }
}
