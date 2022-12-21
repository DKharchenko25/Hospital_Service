package com.epam.hospital.services;

import com.epam.hospital.models.HospitalCard;
import com.epam.hospital.models.Patient;
import com.epam.hospital.repositories.HospitalCardRepository;
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
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospitalCardServiceImplTest {

    @Mock
    private HospitalCardRepository hospitalCardRepository;

    @Mock
    private PatientService patientService;

    @Mock
    private HospitalStaffService hospitalStaffService;

    @Mock
    private EmailService emailService;

    @Mock
    private HospitalCardWriter hospitalCardWriter;

    private HospitalCardService hospitalCardService;

    private HospitalCard hospitalCard;
    private Patient patient;

    private List<HospitalCard> hospitalCards;

    @BeforeEach
    void init() {
        hospitalCardService = new HospitalCardServiceImpl(hospitalCardRepository, patientService, hospitalStaffService, emailService, hospitalCardWriter);
        hospitalCard = new HospitalCard();
        patient = new Patient();
        patient.setId(2L);
        hospitalCards = new ArrayList<>();
    }


    @Test
    void addHospitalCardSuccess() {
        doNothing().when(hospitalCardRepository).addHospitalCard(isA(HospitalCard.class));
        hospitalCardService.addHospitalCard(1L, 1L);
        verify(hospitalCardRepository, times(1)).addHospitalCard(any(HospitalCard.class));
    }

    @Test
    void addHospitalCardMustThrowIllegalArgumentException() {
        when(patientService.getPatientById(1L)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.addHospitalCard(1L, 1L));

        when(hospitalStaffService.getHospitalStaffById(2L)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.addHospitalCard(2L, 2L));

    }

    @ParameterizedTest
    @MethodSource("nullInputValues")
    void addHospitalCardMustThrowNullPointerException(Long patientId, Long doctorId) {
        assertThrows(NullPointerException.class, () -> hospitalCardService.addHospitalCard(patientId, doctorId));
    }

    private static Stream<Arguments> nullInputValues() {
        return Stream.of(
                Arguments.of(null, 1L),
                Arguments.of(1L, null),
                Arguments.of(null, null)
        );
    }

    @Test
    void getHospitalCardByIdSuccess() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.of(hospitalCard));
        assertEquals(hospitalCard, hospitalCardService.getHospitalCardById(1L));
    }

    @ParameterizedTest
    @NullSource
    void getHospitalCardByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalCardService.getHospitalCardById(id));
    }

    @Test
    void getHospitalCardByIdMustThrowIllegalArgumentException() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.getHospitalCardById(1L));
    }

    @Test
    void getAllHospitalCardsSuccess() {
        when(hospitalCardRepository.getAllHospitalCards()).thenReturn(hospitalCards);
        assertEquals(hospitalCards, hospitalCardService.getAllHospitalCards());
    }

    @Test
    void getAllHospitalCardsMustThrowIllegalArgumentException() {
        when(hospitalCardRepository.getAllHospitalCards()).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.getAllHospitalCards());
    }


    @Test
    void getAllPatientHospitalCardsByIdSuccess() {
        when(patientService.getPatientById(2L)).thenReturn(patient);
        when(hospitalCardRepository.getAllPatientHospitalCardsById(patient.getId())).thenReturn(hospitalCards);
        assertEquals(hospitalCards, hospitalCardService.getAllPatientHospitalCardsById(2L));
    }

    @Test
    void getAllPatientHospitalCardsByIdMustThrowIllegalArgumentException() {
        when(patientService.getPatientById(1L)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.getAllPatientHospitalCardsById(1L));
    }

    @ParameterizedTest
    @NullSource
    void getAllPatientHospitalCardsByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalCardService.getAllPatientHospitalCardsById(id));
    }

    @Test
    void deleteHospitalCardByIdSuccess() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.of(hospitalCard));
        doNothing().when(hospitalCardRepository).deleteHospitalCardById(1L);
        hospitalCardService.deleteHospitalCardById(1L);
        verify(hospitalCardRepository, times(1)).deleteHospitalCardById(1L);
    }

    @Test
    void deleteHospitalCardByIdMustThrowIllegalArgumentException() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.deleteHospitalCardById(1L));
    }

    @ParameterizedTest
    @NullSource
    void deleteHospitalCardByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalCardService.deleteHospitalCardById(id));

    }

    @Test
    void insertRecordToPatientProceduresSuccess() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.of(hospitalCard));
        doNothing().when(hospitalCardRepository).updateProceduresById(1L, "test");
        hospitalCardService.insertRecordToPatientProcedures(1L, "test");
        verify(hospitalCardRepository, times(1)).updateProceduresById(1L, "test");
    }

    @Test
    void insertRecordToPatientProceduresMustThrowIllegalArgumentException() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.insertRecordToPatientProcedures(1L, "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidRecordsValues")
    void insertRecordToPatientProceduresMustThrowNullPointerException(Long id, String procedures) {
        assertThrows(NullPointerException.class, () -> hospitalCardService.insertRecordToPatientProcedures(id, procedures));
    }

    private static Stream<Arguments> invalidRecordsValues() {
        return Stream.of(
                Arguments.of(null, "test"),
                Arguments.of(1L, null),
                Arguments.of(null, null)
        );
    }

    @Test
    void insertRecordToPatientMedicationsSuccess() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.of(hospitalCard));
        doNothing().when(hospitalCardRepository).updateMedicationsById(1L, "test");
        hospitalCardService.insertRecordToPatientMedications(1L, "test");
        verify(hospitalCardRepository, times(1)).updateMedicationsById(1L, "test");
    }

    @Test
    void insertRecordToPatientMedicationsMustThrowIllegalArgumentException() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.insertRecordToPatientMedications(1L, "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidRecordsValues")
    void insertRecordToPatientMedicationsMustThrowNullPointerException(Long id, String medications) {
        assertThrows(NullPointerException.class, () -> hospitalCardService.insertRecordToPatientMedications(id, medications));
    }

    @Test
    void insertRecordToPatientOperationsSuccess() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.of(hospitalCard));
        doNothing().when(hospitalCardRepository).updateOperationsById(1L, "test");
        hospitalCardService.insertRecordToPatientOperations(1L, "test");
        verify(hospitalCardRepository, times(1)).updateOperationsById(1L, "test");
    }

    @Test
    void insertRecordToPatientOperationsMustThrowIllegalArgumentException() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.insertRecordToPatientOperations(1L, "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidRecordsValues")
    void insertRecordToPatientOperationsMustThrowNullPointerException(Long id, String operations) {
        assertThrows(NullPointerException.class, () -> hospitalCardService.insertRecordToPatientOperations(id, operations));
    }

    @Test
    void insertRecordToPatientDiagnosisSuccess() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.of(hospitalCard));
        doNothing().when(hospitalCardRepository).updateDiagnosisById(1L, "test");
        hospitalCardService.insertRecordToPatientDiagnosis(1L, "test");
        verify(hospitalCardRepository, times(1)).updateDiagnosisById(1L, "test");
    }

    @Test
    void insertRecordToPatientDiagnosisMustThrowIllegalArgumentException() {
        when(hospitalCardRepository.getHospitalCardById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.insertRecordToPatientDiagnosis(1L, "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidRecordsValues")
    void insertRecordToPatientDiagnosisMustThrowNullPointerException(Long id, String diagnosis) {
        assertThrows(NullPointerException.class, () -> hospitalCardService.insertRecordToPatientDiagnosis(id, diagnosis));
    }

    @Test
    void writeAndSendCardToPatientSuccess() {
        when(patientService.getPatientById(2L)).thenReturn(patient);
        doNothing().when(hospitalCardWriter).writeHospitalCard(patient, hospitalCards, "/test");
        doNothing().when(emailService).sendHospitalCard(any(Patient.class), any(String.class));

        hospitalCardService.writeAndSendCardToPatient(patient.getId(), "/test");
        verify(hospitalCardWriter, times(1)).writeHospitalCard(patient, hospitalCards, "/test");
        verify(emailService, times(1)).sendHospitalCard(patient, "/test");
    }

    @Test
    void writeAndSendCardToPatientMustThrowRuntimeException() {
        lenient().doThrow(RuntimeException.class).when(hospitalCardWriter).writeHospitalCard(patient, hospitalCards, "/test");
        assertThrows(RuntimeException.class, () -> hospitalCardService.writeAndSendCardToPatient(2L, "/test"));

        lenient().doThrow(RuntimeException.class).when(emailService).sendHospitalCard(patient, "/test");
        assertThrows(RuntimeException.class, () -> hospitalCardService.writeAndSendCardToPatient(2L, "/test"));
    }

    @Test
    void writeAndSendCardToPatientMustThrowIllegalArgumentException() {
        when(patientService.getPatientById(1L)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> hospitalCardService.writeAndSendCardToPatient(1L, "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidRecordsValues")
    void writeAndSendCardToPatientMustThrowNullPointerException(Long id, String path) {
        assertThrows(NullPointerException.class, () -> hospitalCardService.writeAndSendCardToPatient(id, path));
    }
}