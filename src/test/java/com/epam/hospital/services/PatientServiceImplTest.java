package com.epam.hospital.services;

import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.data_access_layer.models.Patient;
import com.epam.hospital.data_access_layer.repositories.PatientRepository;
import com.epam.hospital.facades.validators.PasswordEncoder;
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
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private HospitalStaffService hospitalStaffService;

    private PatientService patientService;

    private Patient patient;

    private List<Patient> patients;

    private HospitalStaff hospitalStaff;

    private List<HospitalStaff> hospitalStaffList;

    @BeforeEach
    void init() {
        patientService = new PatientServiceImpl(patientRepository, hospitalStaffService);
        patient = new Patient();
        patient.setId(1L);
        patient.setUsername("TestUsername");
        patient.setPassword("TestPassword@123");
        patients = new ArrayList<>();
        hospitalStaff = new HospitalStaff();
        hospitalStaff.setId(2L);
        hospitalStaff.setNumberOfPatients(10);
        hospitalStaffList = new ArrayList<>();
    }

    @Test
    void addPatientSuccess() {
        when(patientRepository.getPatientByUsername(patient.getUsername())).thenReturn(Optional.empty());
        doNothing().when(patientRepository).addPatient(any(Patient.class));

        String expectedPassword = PasswordEncoder.getEncodedPassword(patient.getPassword());
        patientService.addPatient(patient);
        verify(patientRepository, times(1)).addPatient(patient);
        assertEquals(expectedPassword, patient.getPassword());
    }

    @Test
    void addPatientMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientByUsername(patient.getUsername())).thenReturn(Optional.of(patient));
        assertThrows(IllegalArgumentException.class, () -> patientService.addPatient(patient));
    }

    @ParameterizedTest
    @NullSource
    void addPatientMustThrowNullPointerException(Patient newPatient) {
        assertThrows(NullPointerException.class, () -> patientService.addPatient(newPatient));
    }

    @Test
    void getPatientByIdSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        assertEquals(patient, patientService.getPatientById(patient.getId()));
    }

    @Test
    void getPatientByIdMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.getPatientById(patient.getId()));
    }

    @ParameterizedTest
    @NullSource
    void getPatientByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> patientService.getPatientById(id));
    }

    @Test
    void getPatientByUsernameSuccess() {
        when(patientRepository.getPatientByUsername(patient.getUsername())).thenReturn(Optional.of(patient));
        assertEquals(patient, patientService.getPatientByUsername(patient.getUsername()));
    }

    @Test
    void getPatientByUsernameMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientByUsername(patient.getUsername())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.getPatientByUsername(patient.getUsername()));
    }

    @ParameterizedTest
    @NullSource
    void getPatientByUsernameMustThrowNullPointerException(String username) {
        assertThrows(NullPointerException.class, () -> patientService.getPatientByUsername(username));
    }

    @Test
    void getAllPatientsSuccess() {
        when(patientRepository.getAllPatients()).thenReturn(patients);
        assertEquals(patients, patientService.getAllPatients());
    }

    @Test
    void getAllPatientsMustThrowIllegalArgumentException() {
        when(patientRepository.getAllPatients()).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> patientService.getAllPatients());
    }

    @Test
    void getAllPatientsPageableSuccess() {
        when(patientRepository.getAllPatientsPageable(4, 5)).thenReturn(patients);
        assertEquals(patients, patientService.getAllPatientsPageable(4, 5));
    }

    @Test
    void getAllPatientsPageableMustThrowIllegalArgumentException() {
        when(patientRepository.getAllPatientsPageable(4, 5)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> patientService.getAllPatientsPageable(4, 5));
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void getAllPatientsPageableMustThrowNullPointerException(Integer offset, Integer numberOfRecords) {
        assertThrows(NullPointerException.class, () -> patientService.getAllPatientsPageable(offset, numberOfRecords));

    }

    private static Stream<Arguments> invalidInputs() {
        return Stream.of(
                Arguments.of(4, null),
                Arguments.of(null, 4),
                Arguments.of(null, null)
        );
    }


    @Test
    void getNumberOfRows() {
        when(patientRepository.getNumberOfRows()).thenReturn(5);
        assertEquals(5, patientService.getNumberOfRows());
    }

    @Test
    void getAllPatientsByHospitalStaffIdSuccess() {
        when(patientRepository.getAllPatientsByHospitalStaffId(1L)).thenReturn(patients);
        assertEquals(patients, patientService.getAllPatientsByHospitalStaffId(1L));
    }


    @ParameterizedTest
    @NullSource
    void getAllPatientsByHospitalStaffIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> patientService.getAllPatientsByHospitalStaffId(id));
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void getAllPatientsSortedAlphabeticallyMustThrowNullPointerException(Integer offset, Integer numberOfRecords) {
        assertThrows(NullPointerException.class, () -> patientService.getAllPatientsSortedAlphabetically(offset, numberOfRecords));
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void getAllPatientsSortedByBirthDateMustThrowNullPointerException(Integer offset, Integer numberOfRecords) {
        assertThrows(NullPointerException.class, () -> patientService.getAllPatientsSortedByBirthDate(offset, numberOfRecords));
    }

    @Test
    void appointPatientToDoctorSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        when(hospitalStaffService.getHospitalStaffById(hospitalStaff.getId())).thenReturn(hospitalStaff);
        doNothing().when(hospitalStaffService).updateNumberOfPatientsById(hospitalStaff.getId(), hospitalStaff.getNumberOfPatients() + 1);
        doNothing().when(patientRepository).appointPatientToDoctor(patient.getId(), hospitalStaff.getId());

        patientService.appointPatientToDoctor(patient.getId(), hospitalStaff.getId());
        verify(patientRepository, times(1)).appointPatientToDoctor(patient.getId(), hospitalStaff.getId());
        verify(hospitalStaffService, times(1)).updateNumberOfPatientsById(hospitalStaff.getId(), hospitalStaff.getNumberOfPatients() + 1);

    }

    @Test
    void appointPatientToDoctorMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.appointPatientToDoctor(patient.getId(), hospitalStaff.getId()));

        lenient().when(hospitalStaffService.getHospitalStaffById(hospitalStaff.getId())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> patientService.appointPatientToDoctor(patient.getId(), hospitalStaff.getId()));

    }

    @ParameterizedTest
    @MethodSource("invalidIds")
    void appointPatientToDoctorMustThrowNullPointerException(Long patientId, Long doctorId) {
        assertThrows(NullPointerException.class, () -> patientService.appointPatientToDoctor(patientId, doctorId));
    }

    private static Stream<Arguments> invalidIds() {
        return Stream.of(
                Arguments.of(1L, null),
                Arguments.of(null, 1L),
                Arguments.of(null, null)
        );
    }


    @Test
    void dischargePatientByIdSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        lenient().doNothing().when(patientRepository).dischargePatientFromDoctor(any(Long.class), any(Long.class));
        lenient().doNothing().when(hospitalStaffService).updateNumberOfPatientsById(any(Long.class), any(Long.class));
        lenient().when(hospitalStaffService.getAllHospitalStaff()).thenReturn(hospitalStaffList);
        patientService.dischargePatientById(patient.getId());

        verify(patientRepository, times(hospitalStaffList.size())).deletePatientById(patient.getId());
        verify(hospitalStaffService, times(hospitalStaffList.size())).updateNumberOfPatientsById(hospitalStaff.getId(), hospitalStaff.getNumberOfPatients() - 1);
    }


    @Test
    void dischargePatientByIdMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.dischargePatientById(patient.getId()));

    }

    @ParameterizedTest
    @NullSource
    void dischargePatientByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> patientService.dischargePatientById(id));
    }

    @Test
    void deletePatientByIdSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).deletePatientById(patient.getId());
        patientService.deletePatientById(patient.getId());
        verify(patientRepository, times(1)).deletePatientById(patient.getId());
    }

    @Test
    void deletePatientByIdMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.deletePatientById(patient.getId()));
    }

    @ParameterizedTest
    @NullSource
    void deletePatientByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> patientService.deletePatientById(id));
    }

    @Test
    void updatePatientUsernameByIdSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        when(patientRepository.getPatientByUsername(patient.getUsername())).thenReturn(Optional.empty());
        doNothing().when(patientRepository).updatePatientUsernameById(patient.getId(), patient.getUsername());

        patientService.updatePatientUsernameById(patient.getId(), patient.getUsername());
        verify(patientRepository, times(1)).updatePatientUsernameById(patient.getId(), patient.getUsername());
    }


    @Test
    void updatePatientUsernameByIdMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.updatePatientUsernameById(patient.getId(), "test"));

        when(patientRepository.getPatientByUsername(patient.getUsername())).thenReturn(Optional.of(patient));
        assertThrows(IllegalArgumentException.class, () -> patientService.updatePatientUsernameById(patient.getId(), patient.getUsername()));

    }

    @ParameterizedTest
    @MethodSource("invalidUpdateArguments")
    void updatePatientUsernameByIdMustThrowNullPointerException(Long id, String username) {
        assertThrows(NullPointerException.class, () -> patientService.updatePatientUsernameById(id, username));
    }

    private static Stream<Arguments> invalidUpdateArguments() {
        return Stream.of(
                Arguments.of(1L, null),
                Arguments.of(null, "test"),
                Arguments.of(null, null)
        );
    }

    @Test
    void updatePatientPasswordByIdSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        String expectedPassword = PasswordEncoder.getEncodedPassword(patient.getPassword());
        doNothing().when(patientRepository).updatePatientPasswordById(any(Long.class), any(String.class));
        patientService.updatePatientPasswordById(patient.getId(), patient.getPassword());
        verify(patientRepository, times(1)).updatePatientPasswordById(patient.getId(), expectedPassword);

    }

    @Test
    void updatePatientPasswordByIdMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.updatePatientPasswordById(patient.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateArguments")
    void updatePatientPasswordByIdMustThrowNullPointerException(Long id, String password) {
        assertThrows(NullPointerException.class, () -> patientService.updatePatientUsernameById(id, password));
    }

    @Test
    void updatePatientFirstNameByIdSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).updatePatientFirstNameById(patient.getId(), "test");

        patientService.updatePatientFirstNameById(patient.getId(), "test");
        verify(patientRepository, times(1)).updatePatientFirstNameById(patient.getId(), "test");
    }

    @Test
    void updatePatientFirstNameByIdMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.updatePatientFirstNameById(patient.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateArguments")
    void updatePatientFirstNameByIdMustThrowNullPointerException(Long id, String firstName) {
        assertThrows(NullPointerException.class, () -> patientService.updatePatientFirstNameById(id, firstName));
    }

    @Test
    void updatePatientLastNameByIdSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).updatePatientLastNameById(patient.getId(), "test");

        patientService.updatePatientLastNameById(patient.getId(), "test");
        verify(patientRepository, times(1)).updatePatientLastNameById(patient.getId(), "test");
    }

    @Test
    void updatePatientLastNameByIdMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.updatePatientLastNameById(patient.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateArguments")
    void updatePatientLastNameByIdMustThrowNullPointerException(Long id, String lastName) {
        assertThrows(NullPointerException.class, () -> patientService.updatePatientLastNameById(id, lastName));
    }

    @Test
    void updatePatientBirthDateByIdSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).updatePatientBirthDateById(patient.getId(), "test");

        patientService.updatePatientBirthDateById(patient.getId(), "test");
        verify(patientRepository, times(1)).updatePatientBirthDateById(patient.getId(), "test");
    }

    @Test
    void updatePatientBirthDateByIdMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.updatePatientBirthDateById(patient.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateArguments")
    void updatePatientBirthDateByIdMustThrowNullPointerException(Long id, String birthDate) {
        assertThrows(NullPointerException.class, () -> patientService.updatePatientBirthDateById(id, birthDate));
    }

    @Test
    void updatePatientEmailByIdSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).updatePatientEmailById(patient.getId(), "test");

        patientService.updatePatientEmailById(patient.getId(), "test");
        verify(patientRepository, times(1)).updatePatientEmailById(patient.getId(), "test");
    }

    @Test
    void updatePatientEmailByIdMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.updatePatientEmailById(patient.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateArguments")
    void updatePatientEmailByIdMustThrowNullPointerException(Long id, String email) {
        assertThrows(NullPointerException.class, () -> patientService.updatePatientEmailById(id, email));
    }

    @Test
    void updatePatientPhoneNumberByIdSuccess() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).updatePatientPhoneNumberById(patient.getId(), "test");

        patientService.updatePatientPhoneNumberById(patient.getId(), "test");
        verify(patientRepository, times(1)).updatePatientPhoneNumberById(patient.getId(), "test");
    }

    @Test
    void updatePatientPhoneNumberByIdMustThrowIllegalArgumentException() {
        when(patientRepository.getPatientById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.updatePatientPhoneNumberById(patient.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateArguments")
    void updatePatientPhoneNumberByIdMustThrowNullPointerException(Long id, String phoneNumber) {
        assertThrows(NullPointerException.class, () -> patientService.updatePatientPhoneNumberById(id, phoneNumber));
    }

}