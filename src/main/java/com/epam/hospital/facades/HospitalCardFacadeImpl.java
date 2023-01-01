package com.epam.hospital.facades;

import com.epam.hospital.facades.converters.HospitalCardConverter;
import com.epam.hospital.facades.dtos.HospitalCardDto;
import com.epam.hospital.services.HospitalCardService;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.hospital.facades.converters.HospitalCardConverter.convertHospitalCardToDto;

public class HospitalCardFacadeImpl implements HospitalCardFacade {

    private final HospitalCardService hospitalCardService;

    public HospitalCardFacadeImpl(HospitalCardService hospitalCardService) {
        this.hospitalCardService = hospitalCardService;
    }

    @Override
    public HospitalCardDto getHospitalCardById(@NonNull Long id) {
        return convertHospitalCardToDto(hospitalCardService.getHospitalCardById(id));
    }

    @Override
    public List<HospitalCardDto> getAllHospitalCards() {
        return hospitalCardService.getAllHospitalCards().stream()
                .map(HospitalCardConverter::convertHospitalCardToDto).collect(Collectors.toList());
    }

    @Override
    public void addHospitalCard(@NonNull Long patientId, @NonNull Long hospitalStaffId) {
        hospitalCardService.addHospitalCard(patientId, hospitalStaffId);
    }

    @Override
    public void deleteHospitalCardById(@NonNull Long id) {
        hospitalCardService.deleteHospitalCardById(id);
    }

    @Override
    public void insertRecordToPatientProcedures(@NonNull Long cardId, @NonNull String procedure) {
        hospitalCardService.insertRecordToPatientProcedures(cardId, procedure);
    }

    @Override
    public void insertRecordToPatientMedications(@NonNull Long cardId, @NonNull String medication) {
        hospitalCardService.insertRecordToPatientMedications(cardId, medication);
    }

    @Override
    public void insertRecordToPatientOperations(@NonNull Long cardId, @NonNull String operation) {
        hospitalCardService.insertRecordToPatientOperations(cardId, operation);
    }

    @Override
    public void insertRecordToPatientDiagnosis(@NonNull Long cardId, @NonNull String diagnosis) {
        hospitalCardService.insertRecordToPatientDiagnosis(cardId, diagnosis);
    }

    @Override
    public void writeAndSendCardToPatient(@NonNull Long patientId) {
        hospitalCardService.writeAndSendCardToPatient(patientId);

    }
}
