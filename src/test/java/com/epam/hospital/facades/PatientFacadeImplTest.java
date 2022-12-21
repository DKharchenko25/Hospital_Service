package com.epam.hospital.facades;

import com.epam.hospital.converters.PatientConverter;
import com.epam.hospital.dtos.PatientDto;
import com.epam.hospital.models.Patient;
import com.epam.hospital.services.PatientService;
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
class PatientFacadeImplTest {

    private PatientFacade patientFacade;

    @Mock
    private PatientService patientService;

    private Patient patient;

    private PatientDto patientDto;

    private List<Patient> patients;

    private List<PatientDto> patientDtoList;

    @BeforeEach
    void init() {
        patientFacade = new PatientFacadeImpl(patientService);
        patientDto = new PatientDto();
        patientDto.setUsername("Username1");
        patientDto.setPassword("TestPassword@123");
        patientDto.setFirstName("John");
        patientDto.setLastName("Dow");
        patientDto.setBirthDate("1995-02-26");
        patientDto.setEmail("test@mail.com");
        patientDto.setPhoneNumber("+380973421234");
        patient = PatientConverter.convertDtoToPatient(patientDto);
        patients = new ArrayList<>();
        patients.add(patient);
        patientDtoList = patients.stream().map(PatientConverter::convertPatientToDto).collect(Collectors.toList());
    }

    @Test
    void addPatientSuccess() {
        doNothing().when(patientService).addPatient(any(Patient.class));
        patientFacade.addPatient(patientDto);
        verify(patientService, times(1)).addPatient(any(Patient.class));
    }

    @ParameterizedTest
    @MethodSource("invalidUsernames")
    void addPatientMustThrowIllegalArgumentExceptionInvalidUsername(String username) {
        patientDto.setUsername(username);
        assertThrows(IllegalArgumentException.class, () -> patientFacade.addPatient(patientDto));
    }

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    void addPatientMustThrowIllegalArgumentExceptionInvalidPassword(String password) {
        patientDto.setPassword(password);
        assertThrows(IllegalArgumentException.class, () -> patientFacade.addPatient(patientDto));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void addPatientMustThrowIllegalArgumentExceptionInvalidFirstName(String firstName) {
        patientDto.setFirstName(firstName);
        assertThrows(IllegalArgumentException.class, () -> patientFacade.addPatient(patientDto));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void addPatientMustThrowIllegalArgumentExceptionInvalidLastName(String lastName) {
        patientDto.setLastName(lastName);
        assertThrows(IllegalArgumentException.class, () -> patientFacade.addPatient(patientDto));
    }

    @ParameterizedTest
    @MethodSource("invalidBirthDates")
    void addPatientMustThrowIllegalArgumentExceptionInvalidBirthDate(String birthDate) {
        patientDto.setBirthDate(birthDate);
        assertThrows(IllegalArgumentException.class, () -> patientFacade.addPatient(patientDto));
    }

    @ParameterizedTest
    @MethodSource("invalidEmails")
    void addPatientMustThrowIllegalArgumentExceptionInvalidEmail(String email) {
        patientDto.setEmail(email);
        assertThrows(IllegalArgumentException.class, () -> patientFacade.addPatient(patientDto));
    }

    @ParameterizedTest
    @MethodSource("invalidPhoneNumbers")
    void addPatientMustThrowIllegalArgumentExceptionInvalidPhoneNumber(String phoneNumber) {
        patientDto.setPhoneNumber(phoneNumber);
        assertThrows(IllegalArgumentException.class, () -> patientFacade.addPatient(patientDto));
    }

    private static Stream<String> invalidUsernames() {
        return Stream.of(" ", "123", "?---_", "min", "Too_123_Long_123_Username_123_654321");
    }

    private static Stream<String> invalidPasswords() {
        return Stream.of(" ", "short@1", "onlylowercase1@", "ONLYUPPERCASE1@",
                "NoDigits@#", "1234567@#", "NoSpecialSymbols12", "Too(Long)Password@123#6789");
    }

    private static Stream<String> invalidNames() {
        return Stream.of(" ", "1223", "?@$#", "Tom123");
    }


    private static Stream<String> invalidBirthDates() {
        return Stream.of(" ", "1223", "?@$#", "1945.12.01");
    }

    private static Stream<String> invalidEmails() {
        return Stream.of(" ", "1223", "?@$#", "examplemail.com", "example@mail,com", "@examplemail.com");
    }

    private static Stream<String> invalidPhoneNumbers() {
        return Stream.of(" ", "1223", "?@$#", "+380ter123321",
                "+390974321212", "+3809593350860830568");
    }

    @ParameterizedTest
    @NullSource
    void addPatientMustThrowNullPointerException(PatientDto dto) {
        assertThrows(NullPointerException.class, () -> patientFacade.addPatient(dto));
    }

    @Test
    void getPatientByIdSuccess() {
        when(patientService.getPatientById(1L)).thenReturn(patient);
        assertEquals(patientDto, patientFacade.getPatientById(1L));
    }

    @ParameterizedTest
    @NullSource
    void getPatientByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> patientFacade.getPatientById(id));
    }

    @Test
    void getPatientByUsernameSuccess() {
        when(patientService.getPatientByUsername(patient.getUsername())).thenReturn(patient);
        assertEquals(patientDto, patientFacade.getPatientByUsername(patient.getUsername()));
    }

    @ParameterizedTest
    @NullSource
    void getPatientByUsernameMustThrowNullPointerException(String username) {
        assertThrows(NullPointerException.class, () -> patientFacade.getPatientByUsername(username));
    }

    @Test
    void getAllPatientsByHospitalStaffIdSuccess() {
        when(patientService.getAllPatientsByHospitalStaffId(1L)).thenReturn(patients);
        assertEquals(patientDtoList, patientFacade.getAllPatientsByHospitalStaffId(1L));
    }

    @ParameterizedTest
    @NullSource
    void getAllPatientsByHospitalStaffIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> patientFacade.getAllPatientsByHospitalStaffId(id));
    }

    @Test
    void getAllPatientsSuccess() {
        when(patientService.getAllPatients()).thenReturn(patients);
        assertEquals(patientDtoList, patientFacade.getAllPatients());
    }

    @Test
    void getAllPatientsPageableSuccess() {
        when(patientService.getAllPatientsPageable(3, 10)).thenReturn(patients);
        assertEquals(patientDtoList, patientFacade.getAllPatientsPageable(3, 10));
    }

    @ParameterizedTest
    @MethodSource("nullInputs")
    void getAllPatientsPageableMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> patientFacade.getAllPatientsPageable(offset, numberOfRows));

    }

    private static Stream<Arguments> nullInputs() {
        return Stream.of(
                Arguments.of(3, null),
                Arguments.of(null, 5),
                Arguments.of(null, null)
        );
    }

    @Test
    void getNumberOfRowsSuccess() {
        when(patientService.getNumberOfRows()).thenReturn(20);
        assertEquals(20, patientFacade.getNumberOfRows());
    }


    @Test
    void getAllPatientsSortedAlphabeticallySuccess() {
        when(patientService.getAllPatientsSortedAlphabetically(3, 10)).thenReturn(patients);
        assertEquals(patientDtoList, patientFacade.getAllPatientsSortedAlphabetically(3, 10));
    }

    @ParameterizedTest
    @MethodSource("nullInputs")
    void getAllPatientsSortedAlphabeticallyMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> patientFacade.getAllPatientsSortedAlphabetically(offset, numberOfRows));
    }

    @Test
    void getAllPatientsSortedByBirthDateSuccess() {
        when(patientService.getAllPatientsSortedByBirthDate(3, 10)).thenReturn(patients);
        assertEquals(patientDtoList, patientFacade.getAllPatientsSortedByBirthDate(3, 10));
    }

    @ParameterizedTest
    @MethodSource("nullInputs")
    void getAllPatientsSortedByBirthDateMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> patientFacade.getAllPatientsSortedByBirthDate(offset, numberOfRows));
    }

    @Test
    void appointPatientToDoctorSuccess() {
        doNothing().when(patientService).appointPatientToDoctor(1L, 1L);
        patientFacade.appointPatientToDoctor(1L, 1L);
        verify(patientService, times(1)).appointPatientToDoctor(1L, 1L);
    }

    @ParameterizedTest
    @MethodSource("nullIds")
    void appointPatientToDoctorMustThrowNullPointerException(Long patientId, Long doctorId) {
        assertThrows(NullPointerException.class, () -> patientFacade.appointPatientToDoctor(patientId, doctorId));
    }

    private static Stream<Arguments> nullIds() {
        return Stream.of(
                Arguments.of(1L, null),
                Arguments.of(null, 1L),
                Arguments.of(null, null)
        );
    }

    @Test
    void dischargePatientByIdSuccess() {
        doNothing().when(patientService).dischargePatientById(1L);
        patientFacade.dischargePatientById(1L);
        verify(patientService, times(1)).dischargePatientById(1L);
    }

    @ParameterizedTest
    @NullSource
    void dischargePatientByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> patientFacade.dischargePatientById(id));
    }

    @Test
    void deletePatientByIdSuccess() {
        doNothing().when(patientService).deletePatientById(1L);
        patientFacade.deletePatientById(1L);
        verify(patientService, times(1)).deletePatientById(1L);
    }

    @ParameterizedTest
    @NullSource
    void deletePatientByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> patientFacade.deletePatientById(id));
    }

    @Test
    void updatePatientUsernameByIdSuccess() {
        doNothing().when(patientService).updatePatientUsernameById(1L, patient.getUsername());
        patientFacade.updatePatientUsernameById(1L, patient.getUsername());
        verify(patientService, times(1)).updatePatientUsernameById(1L, patient.getUsername());
    }

    @ParameterizedTest
    @MethodSource("invalidUsernames")
    void updatePatientUsernameByIdMustThrowIllegalArgumentException(String username) {
        assertThrows(IllegalArgumentException.class, () -> patientFacade.updatePatientUsernameById(1L, username));
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updatePatientUsernameByIdMustThrowNullPointerException(Long id, String username) {
        assertThrows(NullPointerException.class, () -> patientFacade.updatePatientUsernameById(id, username));
    }

    private static Stream<Arguments> nullUpdateInputs() {
        return Stream.of(
                Arguments.of(1L, null),
                Arguments.of(null, "test"),
                Arguments.of(null, null)
        );
    }

    @Test
    void updatePatientPasswordByIdSuccess() {
        doNothing().when(patientService).updatePatientPasswordById(1L, patient.getPassword());
        patientFacade.updatePatientPasswordById(1L, patient.getPassword());
        verify(patientService, times(1)).updatePatientPasswordById(1L, patient.getPassword());
    }

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    void updatePatientPasswordByIdMustThrowIllegalArgumentException(String password) {
        assertThrows(IllegalArgumentException.class, () -> patientFacade.updatePatientPasswordById(1L, password));
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updatePatientPasswordByIdMustThrowNullPointerException(Long id, String password) {
        assertThrows(NullPointerException.class, () -> patientFacade.updatePatientPasswordById(id, password));
    }

    @ParameterizedTest
    @MethodSource("validNames")
    void updatePatientFirstNameByIdSuccess(String firstName) {
        doNothing().when(patientService).updatePatientFirstNameById(1L, firstName);
        patientFacade.updatePatientFirstNameById(1L, firstName);
        verify(patientService, times(1)).updatePatientFirstNameById(1L, firstName);
    }

    private static Stream<String> validNames() {
        return Stream.of("John", "Іванка", "Robert", "Василь", "Michael", "Мар'яна");
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void updatePatientFirstNameByIdMustThrowIllegalArgumentException(String firstName) {
        assertThrows(IllegalArgumentException.class, () -> patientFacade.updatePatientFirstNameById(1L, firstName));
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updatePatientFirstNameByIdMustThrowNullPointerException(Long id, String firstName) {
        assertThrows(NullPointerException.class, () -> patientFacade.updatePatientFirstNameById(id, firstName));
    }

    @ParameterizedTest
    @MethodSource("validNames")
    void updatePatientLastNameByIdSuccess(String lastName) {
        doNothing().when(patientService).updatePatientLastNameById(1L, lastName);
        patientFacade.updatePatientLastNameById(1L, lastName);
        verify(patientService, times(1)).updatePatientLastNameById(1L, lastName);
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void updatePatientLastNameByIdMustThrowIllegalArgumentException(String lastName) {
        assertThrows(IllegalArgumentException.class, () -> patientFacade.updatePatientLastNameById(1L, lastName));
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updatePatientLastNameByIdMustThrowNullPointerException(Long id, String lastName) {
        assertThrows(NullPointerException.class, () -> patientFacade.updatePatientLastNameById(id, lastName));
    }

    @Test
    void updatePatientBirthDateByIdSuccess() {
        doNothing().when(patientService).updatePatientBirthDateById(1L, patient.getBirthDate());
        patientFacade.updatePatientBirthDateById(1L, patient.getBirthDate());
        verify(patientService, times(1)).updatePatientBirthDateById(1L, patient.getBirthDate());

    }

    @ParameterizedTest
    @MethodSource("invalidBirthDates")
    void updatePatientBirthDateByIdMustThrowIllegalArgumentException(String birthDate) {
        assertThrows(IllegalArgumentException.class, () -> patientFacade.updatePatientBirthDateById(1L, birthDate));
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updatePatientBirthDateByIdMustThrowNullPointerException(Long id, String birthDate) {
        assertThrows(NullPointerException.class, () -> patientFacade.updatePatientBirthDateById(id, birthDate));
    }

    @Test
    void updatePatientEmailByIdSuccess() {
        doNothing().when(patientService).updatePatientEmailById(1L, patient.getEmail());
        patientFacade.updatePatientEmailById(1L, patient.getEmail());
        verify(patientService, times(1)).updatePatientEmailById(1L, patient.getEmail());
    }

    @ParameterizedTest
    @MethodSource("invalidEmails")
    void updatePatientEmailByIdMustThrowIllegalArgumentException(String email) {
        assertThrows(IllegalArgumentException.class, () -> patientFacade.updatePatientEmailById(1L, email));
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updatePatientEmailByIdMustThrowNullPointerException(Long id, String email) {
        assertThrows(NullPointerException.class, () -> patientFacade.updatePatientEmailById(id, email));
    }

    @Test
    void updatePatientPhoneNumberByIdSuccess() {
        doNothing().when(patientService).updatePatientPhoneNumberById(1L, patient.getPhoneNumber());
        patientFacade.updatePatientPhoneNumberById(1L, patient.getPhoneNumber());
        verify(patientService, times(1)).updatePatientPhoneNumberById(1L, patient.getPhoneNumber());
    }

    @ParameterizedTest
    @MethodSource("invalidPhoneNumbers")
    void updatePatientPhoneNumberByIdMustThrowIllegalArgumentException(String phoneNumber) {
        assertThrows(IllegalArgumentException.class, () -> patientFacade.updatePatientPhoneNumberById(1L, phoneNumber));
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updatePatientPhoneNumberByIdMustThrowNullPointerException(Long id, String phoneNumber) {
        assertThrows(NullPointerException.class, () -> patientFacade.updatePatientPhoneNumberById(id, phoneNumber));
    }

}