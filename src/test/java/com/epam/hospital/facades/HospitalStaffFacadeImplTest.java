package com.epam.hospital.facades;

import com.epam.hospital.converters.HospitalStaffConverter;
import com.epam.hospital.dtos.HospitalStaffDto;
import com.epam.hospital.models.HospitalStaff;
import com.epam.hospital.services.HospitalStaffService;
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
class HospitalStaffFacadeImplTest {

    private HospitalStaffFacade hospitalStaffFacade;

    @Mock
    private HospitalStaffService hospitalStaffService;

    private HospitalStaff hospitalStaff;

    private HospitalStaffDto dto;

    private List<HospitalStaff> hospitalStaffList;

    private List<HospitalStaffDto> hospitalStaffDtoList;

    @BeforeEach
    void init() {
        hospitalStaffFacade = new HospitalStaffFacadeImpl(hospitalStaffService);
        dto = new HospitalStaffDto();
        dto.setUsername("Username1");
        dto.setPassword("TestPassword@123");
        dto.setFirstName("John");
        dto.setLastName("Dow");
        hospitalStaff = HospitalStaffConverter.convertDtoToHospitalStaff(dto);
        hospitalStaffList = new ArrayList<>();
        hospitalStaffDtoList = hospitalStaffList.stream().map(HospitalStaffConverter::convertHospitalStaffToDto)
                .collect(Collectors.toList());
    }


    @Test
    void addHospitalStaffSuccess() {
        doNothing().when(hospitalStaffService).addHospitalStaff(any(HospitalStaff.class));
        hospitalStaffFacade.addHospitalStaff(dto);
        verify(hospitalStaffService, times(1)).addHospitalStaff(any(HospitalStaff.class));
    }

    @ParameterizedTest
    @MethodSource("invalidUsernames")
    void addHospitalStaffMustThrowIllegalArgumentExceptionInvalidUsername(String username) {
        dto.setUsername(username);
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffFacade.addHospitalStaff(dto));
    }

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    void addHospitalStaffMustThrowIllegalArgumentExceptionInvalidPassword(String password) {
        dto.setPassword(password);
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffFacade.addHospitalStaff(dto));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void addHospitalStaffMustThrowIllegalArgumentExceptionInvalidFirstName(String firstName) {
        dto.setFirstName(firstName);
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffFacade.addHospitalStaff(dto));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void addHospitalStaffMustThrowIllegalArgumentExceptionInvalidLastName(String lastName) {
        dto.setLastName(lastName);
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffFacade.addHospitalStaff(dto));
    }

    @ParameterizedTest
    @NullSource
    void addHospitalStaffMustThrowNullPointerException(HospitalStaffDto dto) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.addHospitalStaff(dto));
    }

    @Test
    void getHospitalStaffByIdSuccess() {
        when(hospitalStaffService.getHospitalStaffById(1L)).thenReturn(hospitalStaff);
        assertEquals(HospitalStaffConverter.convertHospitalStaffToDto(hospitalStaff), hospitalStaffFacade.getHospitalStaffById(1L));
    }

    @ParameterizedTest
    @NullSource
    void getHospitalStaffByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.getHospitalStaffById(id));
    }


    @Test
    void getHospitalStaffByUsernameSuccess() {
        when(hospitalStaffService.getHospitalStaffByUsername(hospitalStaff.getUsername())).thenReturn(hospitalStaff);
        assertEquals(HospitalStaffConverter.convertHospitalStaffToDto(hospitalStaff), hospitalStaffFacade.getHospitalStaffByUsername(hospitalStaff.getUsername()));
    }

    @ParameterizedTest
    @NullSource
    void getHospitalStaffByUsernameMustThrowNullPointerException(String username) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.getHospitalStaffByUsername(username));
    }

    @Test
    void getAllHospitalStaffSuccess() {
        when(hospitalStaffService.getAllHospitalStaff()).thenReturn(hospitalStaffList);
        assertEquals(hospitalStaffDtoList, hospitalStaffFacade.getAllHospitalStaff());
    }

    @Test
    void getAllHospitalStaffPageableSuccess() {
        when(hospitalStaffService.getAllHospitalStaffPageable(3, 10)).thenReturn(hospitalStaffList);
        assertEquals(hospitalStaffDtoList, hospitalStaffFacade.getAllHospitalStaffPageable(3, 10));
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void getAllHospitalStaffPageableMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.getAllHospitalStaffPageable(offset, numberOfRows));

    }

    private static Stream<Arguments> invalidInputs() {
        return Stream.of(
                Arguments.of(3, null),
                Arguments.of(null, 3),
                Arguments.of(null, null)
        );
    }

    @Test
    void getNumberOfRows() {
        when(hospitalStaffService.getNumberOfRows()).thenReturn(10);
        assertEquals(10, hospitalStaffFacade.getNumberOfRows());
    }

    @Test
    void getAllHospitalStaffSortedAlphabeticallySuccess() {
        when(hospitalStaffService.getAllHospitalStaffSortedAlphabetically(3, 10)).thenReturn(hospitalStaffList);
        assertEquals(hospitalStaffDtoList, hospitalStaffFacade.getAllHospitalStaffSortedAlphabetically(3, 10));
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void getAllHospitalStaffSortedAlphabeticallyMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.getAllHospitalStaffSortedAlphabetically(offset, numberOfRows));
    }

    @Test
    void getAllHospitalStaffSortedByCategorySuccess() {
        when(hospitalStaffService.getAllHospitalStaffSortedByCategory(3, 10)).thenReturn(hospitalStaffList);
        assertEquals(hospitalStaffDtoList, hospitalStaffFacade.getAllHospitalStaffSortedByCategory(3, 10));
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void getAllHospitalStaffSortedByCategoryMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.getAllHospitalStaffSortedByCategory(offset, numberOfRows));
    }

    @Test
    void getAllHospitalStaffSortedByNumberOfPatientsSuccess() {
        when(hospitalStaffService.getAllHospitalStaffSortedByNumberOfPatients(3, 10)).thenReturn(hospitalStaffList);
        assertEquals(hospitalStaffDtoList, hospitalStaffFacade.getAllHospitalStaffSortedByNumberOfPatients(3, 10));
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void getAllHospitalStaffSortedByNumberOfPatientsMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.getAllHospitalStaffSortedByNumberOfPatients(offset, numberOfRows));
    }


    @Test
    void getAllHospitalStaffByPatientIdSuccess() {
        when(hospitalStaffService.getAllHospitalStaffByPatientId(1L)).thenReturn(hospitalStaffList);
        assertEquals(hospitalStaffDtoList, hospitalStaffFacade.getAllHospitalStaffByPatientId(1L));
    }

    @ParameterizedTest
    @NullSource
    void getAllHospitalStaffByPatientIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.getAllHospitalStaffByPatientId(id));
    }


    @Test
    void deleteHospitalStaffByIdSuccess() {
        doNothing().when(hospitalStaffService).deleteHospitalStaffById(any(Long.class));
        hospitalStaffFacade.deleteHospitalStaffById(1L);
        verify(hospitalStaffService, times(1)).deleteHospitalStaffById(1L);
    }

    @ParameterizedTest
    @NullSource
    void deleteHospitalStaffByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.deleteHospitalStaffById(id));
    }

    @Test
    void updateStaffUsernameByIdSuccess() {
        doNothing().when(hospitalStaffService).updateStaffUsernameById(1L, hospitalStaff.getUsername());
        hospitalStaffFacade.updateStaffUsernameById(1L, hospitalStaff.getUsername());
        verify(hospitalStaffService, times(1)).updateStaffUsernameById(1L, hospitalStaff.getUsername());
    }

    @ParameterizedTest
    @MethodSource("invalidUsernames")
    void updateStaffUsernameByIdMustThrowIllegalArgumentException(String username) {
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffFacade.updateStaffUsernameById(1L, username));
    }

    private static Stream<String> invalidUsernames() {
        return Stream.of(" ", "123", "?---_", "min", "Too_123_Long_123_Username_123_654321");
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updateStaffUsernameByIdMustThrowNullPointerException(Long id, String username) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.updateStaffUsernameById(id, username));

    }

    private static Stream<Arguments> nullUpdateInputs() {
        return Stream.of(
                Arguments.of(1L, null),
                Arguments.of(null, "test"),
                Arguments.of(null, null)
        );
    }

    @Test
    void updateStaffPasswordByIdSuccess() {
        doNothing().when(hospitalStaffService).updateStaffPasswordById(1L, hospitalStaff.getPassword());
        hospitalStaffFacade.updateStaffPasswordById(1L, hospitalStaff.getPassword());
        verify(hospitalStaffService, times(1)).updateStaffPasswordById(1L, hospitalStaff.getPassword());
    }

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    void updateStaffPasswordByIdMustThrowIllegalArgumentException(String password) {
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffFacade.updateStaffPasswordById(1L, password));
    }

    private static Stream<String> invalidPasswords() {
        return Stream.of(" ", "short@1", "onlylowercase1@", "ONLYUPPERCASE1@",
                "NoDigits@#", "1234567@#", "NoSpecialSymbols12", "Too(Long)Password@123#6789");
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updateStaffPasswordByIdMustThrowNullPointerException(Long id, String password) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.updateStaffPasswordById(id, password));
    }

    @Test
    void updateStaffRoleByIdSuccess() {
        doNothing().when(hospitalStaffService).updateStaffRoleById(1L, "test");
        hospitalStaffFacade.updateStaffRoleById(1L, "test");
        verify(hospitalStaffService, times(1)).updateStaffRoleById(1L, "test");
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updateStaffRoleByIdMustThrowNullPointerException(Long id, String role) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.updateStaffRoleById(id, role));
    }


    @Test
    void updateStaffCategoryByIdSuccess() {
        doNothing().when(hospitalStaffService).updateStaffCategoryById(1L, "test");
        hospitalStaffFacade.updateStaffCategoryById(1L, "test");
        verify(hospitalStaffService, times(1)).updateStaffCategoryById(1L, "test");
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updateStaffCategoryByIdMustThrowNullPointerException(Long id, String category) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.updateStaffCategoryById(id, category));
    }

    @ParameterizedTest
    @MethodSource("validNames")
    void updateStaffFirstNameByIdSuccess(String firstName) {
        doNothing().when(hospitalStaffService).updateStaffFirstNameById(1L, firstName);
        hospitalStaffFacade.updateStaffFirstNameById(1L, firstName);
        verify(hospitalStaffService, times(1)).updateStaffFirstNameById(1L, firstName);
    }

    private static Stream<String> validNames() {
        return Stream.of("John", "Іванка", "Robert", "Василь", "Michael", "Мар'яна");
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void updateStaffFirstNameByIdMustThrowIllegalArgumentException(String firstName) {
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffFacade.updateStaffFirstNameById(1L, firstName));
    }

    private static Stream<String> invalidNames() {
        return Stream.of(" ", "1223", "?@$#", "Tom123");
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updateStaffFirstNameByIdMustThrowNullPointerException(Long id, String firstName) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.updateStaffFirstNameById(id, firstName));
    }

    @ParameterizedTest
    @MethodSource("validNames")
    void updateStaffLastNameByIdSuccess(String lastName) {
        doNothing().when(hospitalStaffService).updateStaffLastNameById(1L, lastName);
        hospitalStaffFacade.updateStaffLastNameById(1L, lastName);
        verify(hospitalStaffService, times(1)).updateStaffLastNameById(1L, lastName);
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void updateStaffLastNameByIdMustThrowIllegalArgumentException(String lastName) {
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffFacade.updateStaffLastNameById(1L, lastName));
    }

    @ParameterizedTest
    @MethodSource("nullUpdateInputs")
    void updateStaffLastNameByIdMustThrowNullPointerException(Long id, String lastName) {
        assertThrows(NullPointerException.class, () -> hospitalStaffFacade.updateStaffLastNameById(id, lastName));
    }
}