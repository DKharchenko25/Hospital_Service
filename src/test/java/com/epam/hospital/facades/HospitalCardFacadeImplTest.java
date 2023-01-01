package com.epam.hospital.facades;

import com.epam.hospital.data_access_layer.models.*;
import com.epam.hospital.facades.converters.HospitalCardConverter;
import com.epam.hospital.facades.dtos.HospitalCardDto;
import com.epam.hospital.services.HospitalCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospitalCardFacadeImplTest {

    @Mock
    private HospitalCardService hospitalCardService;

    private HospitalCardFacade hospitalCardFacade;

    private HospitalCard hospitalCard;

    private List<HospitalCard> hospitalCards;

    private List<HospitalCardDto> dtos;

    @BeforeEach
    void init() {
        hospitalCardFacade = new HospitalCardFacadeImpl(hospitalCardService);
        hospitalCard = new HospitalCard();
        hospitalCard.setId(1L);
        hospitalCards = new ArrayList<>();
        Patient patient = new Patient(1L);
        HospitalStaff hospitalStaff = new HospitalStaff(1L);
        hospitalStaff.setRole(new Role(1L, "DOCTOR"));
        hospitalStaff.setCategory(new Category(1L, "Oncologist"));
        hospitalCard.setPatient(patient);
        hospitalCard.setDoctor(hospitalStaff);
        dtos = hospitalCards.stream().map(HospitalCardConverter::convertHospitalCardToDto).collect(Collectors.toList());
    }

    @Test
    void getHospitalCardByIdSuccess() {
        when(hospitalCardService.getHospitalCardById(hospitalCard.getId())).thenReturn(hospitalCard);
        HospitalCardDto dto = HospitalCardConverter.convertHospitalCardToDto(hospitalCard);
        assertEquals(dto.getPatient().getId(), hospitalCardFacade.getHospitalCardById(hospitalCard.getId()).getPatient().getId());
        assertEquals(dto.getDoctor().getId(), hospitalCardFacade.getHospitalCardById(hospitalCard.getId()).getDoctor().getId());
    }

    @ParameterizedTest
    @NullSource
    void getHospitalCardByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalCardFacade.getHospitalCardById(id));
    }

    @Test
    void getAllHospitalCardsSuccess() {
        when(hospitalCardService.getAllHospitalCards()).thenReturn(hospitalCards);
        assertEquals(dtos, hospitalCardFacade.getAllHospitalCards());
    }

    @Test
    void addHospitalCardSuccess() {
        doNothing().when(hospitalCardService).addHospitalCard(1L, 1L);
        hospitalCardService.addHospitalCard(1L, 1L);
        verify(hospitalCardService, times(1)).addHospitalCard(1L, 1L);
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void addHospitalCardMustThrowNullPointerException(Long patientId, Long hospitalStaffId) {
        assertThrows(NullPointerException.class, () -> hospitalCardFacade.addHospitalCard(patientId, hospitalStaffId));
    }

    private static Stream<Arguments> invalidInputs() {
        return Stream.of(
                Arguments.of(1L, null),
                Arguments.of(null, 1L),
                Arguments.of(null, null)
        );
    }


    @Test
    void deleteHospitalCardByIdSuccess() {
        doNothing().when(hospitalCardService).deleteHospitalCardById(1L);
        hospitalCardFacade.deleteHospitalCardById(1L);
        verify(hospitalCardService, times(1)).deleteHospitalCardById(1L);
    }

    @ParameterizedTest
    @NullSource
    void deleteHospitalCardByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalCardFacade.deleteHospitalCardById(id));
    }

    @Test
    void insertRecordToPatientProceduresSuccess() {
        doNothing().when(hospitalCardService).insertRecordToPatientProcedures(1L, "test");
        hospitalCardFacade.insertRecordToPatientProcedures(1L, "test");
        verify(hospitalCardService, times(1)).insertRecordToPatientProcedures(1L, "test");
    }

    @ParameterizedTest
    @MethodSource("invalidRecordsSource")
    void insertRecordToPatientProceduresMustThrowNullPointerException(Long cardId, String procedure) {
        assertThrows(NullPointerException.class, () -> hospitalCardFacade.insertRecordToPatientProcedures(cardId, procedure));
    }

    private static Stream<Arguments> invalidRecordsSource() {
        return Stream.of(
                Arguments.of(1L, null),
                Arguments.of(null, "test"),
                Arguments.of(null, null)
        );
    }


    @Test
    void insertRecordToPatientMedicationsSuccess() {
        doNothing().when(hospitalCardService).insertRecordToPatientMedications(1L, "test");
        hospitalCardFacade.insertRecordToPatientMedications(1L, "test");
        verify(hospitalCardService, times(1)).insertRecordToPatientMedications(1L, "test");
    }

    @ParameterizedTest
    @MethodSource("invalidRecordsSource")
    void insertRecordToPatientMedicationsMustThrowNullPointerException(Long cardId, String medications) {
        assertThrows(NullPointerException.class, () -> hospitalCardFacade.insertRecordToPatientMedications(cardId, medications));
    }

    @Test
    void insertRecordToPatientOperationsSuccess() {
        doNothing().when(hospitalCardService).insertRecordToPatientOperations(1L, "test");
        hospitalCardFacade.insertRecordToPatientOperations(1L, "test");
        verify(hospitalCardService, times(1)).insertRecordToPatientOperations(1L, "test");
    }

    @ParameterizedTest
    @MethodSource("invalidRecordsSource")
    void insertRecordToPatientOperationsMustThrowNullPointerException(Long cardId, String operations) {
        assertThrows(NullPointerException.class, () -> hospitalCardFacade.insertRecordToPatientOperations(cardId, operations));
    }

    @Test
    void insertRecordToPatientDiagnosisSuccess() {
        doNothing().when(hospitalCardService).insertRecordToPatientDiagnosis(1L, "test");
        hospitalCardFacade.insertRecordToPatientDiagnosis(1L, "test");
        verify(hospitalCardService, times(1)).insertRecordToPatientDiagnosis(1L, "test");
    }

    @ParameterizedTest
    @MethodSource("invalidRecordsSource")
    void insertRecordToPatientDiagnosisMustThrowNullPointerException(Long cardId, String diagnosis) {
        assertThrows(NullPointerException.class, () -> hospitalCardFacade.insertRecordToPatientDiagnosis(cardId, diagnosis));
    }

    @Test
    void writeAndSendCardToPatientSuccess() {
        doNothing().when(hospitalCardService).writeAndSendCardToPatient(1L);
        hospitalCardFacade.writeAndSendCardToPatient(1L);
        verify(hospitalCardService, times(1)).writeAndSendCardToPatient(1L);
    }

    @Test
    void writeAndSendCardToPatientMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> hospitalCardFacade.writeAndSendCardToPatient(null));
    }
}