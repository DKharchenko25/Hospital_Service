package com.epam.hospital.services;

import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.data_access_layer.repositories.HospitalStaffRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospitalStaffServiceImplTest {

    @Mock
    private HospitalStaffRepository hospitalStaffRepository;

    private HospitalStaffService hospitalStaffService;

    private HospitalStaff hospitalStaff;

    private List<HospitalStaff> hospitalStaffList;

    @BeforeEach
    void init() {
        hospitalStaffService = new HospitalStaffServiceImpl(hospitalStaffRepository);
        hospitalStaff = new HospitalStaff();
        hospitalStaff.setId(1L);
        hospitalStaff.setUsername("TestUsername");
        hospitalStaff.setPassword("TestPassword@12");
        hospitalStaffList = new ArrayList<>();
    }

    @Test
    void addHospitalStaffSuccess() {
        String expectedPassword = PasswordEncoder.getEncodedPassword(hospitalStaff.getPassword());
        doNothing().when(hospitalStaffRepository).addStaff(any(HospitalStaff.class));
        when(hospitalStaffRepository.getHospitalStaffByUsername(hospitalStaff.getUsername())).thenReturn(Optional.empty());

        hospitalStaffService.addHospitalStaff(hospitalStaff);
        verify(hospitalStaffRepository, times(1)).addStaff(hospitalStaff);
        assertEquals(expectedPassword, hospitalStaff.getPassword());
    }

    @ParameterizedTest
    @NullSource
    void addHospitalStaffMustThrowNullPointerException(HospitalStaff hospitalStaff) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.addHospitalStaff(hospitalStaff));
    }

    @Test
    void addHospitalStaffMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffByUsername(hospitalStaff.getUsername())).thenReturn(Optional.of(hospitalStaff));
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.addHospitalStaff(hospitalStaff));
    }

    @Test
    void getHospitalStaffByIdSuccess() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        assertEquals(hospitalStaff, hospitalStaffService.getHospitalStaffById(hospitalStaff.getId()));
    }

    @Test
    void getHospitalStaffByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.getHospitalStaffById(hospitalStaff.getId()));
    }

    @Test
    void getHospitalStaffByUsernameSuccess() {
        when(hospitalStaffRepository.getHospitalStaffByUsername(hospitalStaff.getUsername())).thenReturn(Optional.of(hospitalStaff));
        assertEquals(hospitalStaff, hospitalStaffService.getHospitalStaffByUsername(hospitalStaff.getUsername()));
    }

    @ParameterizedTest
    @NullSource
    void getHospitalStaffByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.getHospitalStaffById(id));
    }

    @Test
    void getHospitalStaffByUsernameMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffByUsername(hospitalStaff.getUsername())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.getHospitalStaffByUsername(hospitalStaff.getUsername()));
    }

    @ParameterizedTest
    @NullSource
    void getHospitalStaffByUsernameMustThrowNullPointerException(String username) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.getHospitalStaffByUsername(username));
    }

    @Test
    void getAllHospitalStaffSuccess() {
        when(hospitalStaffRepository.getAllHospitalStaff()).thenReturn(hospitalStaffList);
        assertEquals(hospitalStaffList, hospitalStaffService.getAllHospitalStaff());

    }

    @Test
    void getAllHospitalStaffMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getAllHospitalStaff()).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.getAllHospitalStaff());
    }

    @Test
    void getAllHospitalStaffPageableSuccess() {
        when(hospitalStaffRepository.getAllHospitalStaffPageable(4, 5)).thenReturn(hospitalStaffList);
        assertEquals(hospitalStaffList, hospitalStaffService.getAllHospitalStaffPageable(4, 5));
    }

    @Test
    void getAllHospitalStaffPageableMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getAllHospitalStaffPageable(4, 5)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.getAllHospitalStaffPageable(4, 5));
    }

    @ParameterizedTest
    @MethodSource("invalidArguments")
    void getAllHospitalStaffPageableMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.getAllHospitalStaffPageable(offset, numberOfRows));
    }

    private static Stream<Arguments> invalidArguments() {
        return Stream.of(
                Arguments.of(4, null),
                Arguments.of(null, 5),
                Arguments.of(null, null)
        );
    }

    @Test
    void getNumberOfRows() {
        when(hospitalStaffRepository.getNumberOfRows()).thenReturn(5);
        assertEquals(5, hospitalStaffService.getNumberOfRows());
    }

    @ParameterizedTest
    @MethodSource("invalidArguments")
    void getAllHospitalStaffSortedAlphabeticallyMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.getAllHospitalStaffSortedAlphabetically(offset, numberOfRows));
    }

    @ParameterizedTest
    @MethodSource("invalidArguments")
    void getAllHospitalStaffSortedByCategoryMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.getAllHospitalStaffSortedByCategory(offset, numberOfRows));
    }

    @ParameterizedTest
    @MethodSource("invalidArguments")
    void getAllHospitalStaffSortedByNumberOfPatientsMustThrowNullPointerException(Integer offset, Integer numberOfRows) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.getAllHospitalStaffSortedByNumberOfPatients(offset, numberOfRows));
    }

    @Test
    void getAllHospitalStaffByPatientIdSuccess() {
        when(hospitalStaffRepository.getAllHospitalStaffByPatientId(1L)).thenReturn(hospitalStaffList);
        assertEquals(hospitalStaffList, hospitalStaffService.getAllHospitalStaffByPatientId(1L));
    }

    @Test
    void getAllHospitalStaffByPatientIdMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getAllHospitalStaffByPatientId(1L)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.getAllHospitalStaffByPatientId(1L));
    }

    @ParameterizedTest
    @NullSource
    void getAllHospitalStaffByPatientIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.getAllHospitalStaffByPatientId(id));
    }

    @Test
    void deleteHospitalStaffByIdSuccess() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        hospitalStaffService.deleteHospitalStaffById(hospitalStaff.getId());
        verify(hospitalStaffRepository, times(1)).deleteStaffById(hospitalStaff.getId());
    }

    @Test
    void deleteHospitalStaffByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.deleteHospitalStaffById(hospitalStaff.getId()));
    }

    @ParameterizedTest
    @NullSource
    void deleteHospitalStaffByIdMustThrowNullPointerException(Long id) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.deleteHospitalStaffById(id));
    }

    @Test
    void updateStaffUsernameByIdSuccess() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffRepository).updateStaffUsernameById(hospitalStaff.getId(), "test");
        hospitalStaffService.updateStaffUsernameById(hospitalStaff.getId(), "test");
        verify(hospitalStaffRepository, times(1)).updateStaffUsernameById(hospitalStaff.getId(), "test");
    }

    @Test
    void updateStaffUsernameByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.updateStaffUsernameById(hospitalStaff.getId(), "test"));

        when(hospitalStaffRepository.getHospitalStaffByUsername(hospitalStaff.getUsername())).thenReturn(Optional.of(hospitalStaff));
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.updateStaffUsernameById(hospitalStaff.getId(), hospitalStaff.getUsername()));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateValues")
    void updateStaffUsernameByIdMustThrowNullPointerException(Long id, String username) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.updateStaffUsernameById(id, username));
    }

    private static Stream<Arguments> invalidUpdateValues() {
        return Stream.of(
                Arguments.of(1L, null),
                Arguments.of(null, "test"),
                Arguments.of(null, null)
        );
    }

    @Test
    void updateStaffPasswordByIdSuccess() {
        String expectedPassword = PasswordEncoder.getEncodedPassword(hospitalStaff.getPassword());
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffRepository).updateStaffPasswordById(any(Long.class), any(String.class));
        hospitalStaffService.updateStaffPasswordById(hospitalStaff.getId(), hospitalStaff.getPassword());
        verify(hospitalStaffRepository, times(1)).updateStaffPasswordById(hospitalStaff.getId(), expectedPassword);
    }

    @Test
    void updateStaffPasswordByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.updateStaffPasswordById(hospitalStaff.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateValues")
    void updateStaffPasswordByIdMustThrowNullPointerException(Long id, String password) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.updateStaffPasswordById(id, password));
    }

    @Test
    void updateStaffRoleByIdSuccess() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffRepository).updateStaffRoleById(hospitalStaff.getId(), "test");
        hospitalStaffService.updateStaffRoleById(hospitalStaff.getId(), "test");
        verify(hospitalStaffRepository, times(1)).updateStaffRoleById(hospitalStaff.getId(), "test");
    }

    @Test
    void updateStaffRoleByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.updateStaffRoleById(hospitalStaff.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateValues")
    void updateStaffRoleByIdMustThrowNullPointerException(Long id, String role) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.updateStaffPasswordById(id, role));
    }

    @Test
    void updateStaffCategoryByIdSuccess() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffRepository).updateStaffCategoryById(hospitalStaff.getId(), "test");
        hospitalStaffService.updateStaffCategoryById(hospitalStaff.getId(), "test");
        verify(hospitalStaffRepository, times(1)).updateStaffCategoryById(hospitalStaff.getId(), "test");
    }

    @Test
    void updateStaffCategoryByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.updateStaffCategoryById(hospitalStaff.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateValues")
    void updateStaffCategoryByIdMustThrowNullPointerException(Long id, String category) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.updateStaffCategoryById(id, category));
    }

    @Test
    void updateStaffFirstNameByIdSuccess() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffRepository).updateStaffFirstNameById(hospitalStaff.getId(), "test");
        hospitalStaffService.updateStaffFirstNameById(hospitalStaff.getId(), "test");
        verify(hospitalStaffRepository, times(1)).updateStaffFirstNameById(hospitalStaff.getId(), "test");
    }

    @Test
    void updateStaffFirstNameByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.updateStaffFirstNameById(hospitalStaff.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateValues")
    void updateStaffFirstNameByIdMustThrowNullPointerException(Long id, String firstName) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.updateStaffFirstNameById(id, firstName));
    }

    @Test
    void updateStaffLastNameById() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffRepository).updateStaffLastNameById(hospitalStaff.getId(), "test");
        hospitalStaffService.updateStaffLastNameById(hospitalStaff.getId(), "test");
        verify(hospitalStaffRepository, times(1)).updateStaffLastNameById(hospitalStaff.getId(), "test");
    }

    @Test
    void updateStaffLastNameByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.updateStaffLastNameById(hospitalStaff.getId(), "test"));
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateValues")
    void updateStaffLastNameByIdMustThrowNullPointerException(Long id, String lastName) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.updateStaffLastNameById(id, lastName));
    }

    @Test
    void updateNumberOfPatientsByIdSuccess() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffRepository).updateStaffNumberOfPatientsById(hospitalStaff.getId(), 5L);
        hospitalStaffService.updateNumberOfPatientsById(hospitalStaff.getId(), 5L);
        verify(hospitalStaffRepository, times(1)).updateStaffNumberOfPatientsById(hospitalStaff.getId(), 5L);
    }

    @Test
    void updateNumberOfPatientsByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffService.updateNumberOfPatientsById(hospitalStaff.getId(), 5L));
    }

    @ParameterizedTest
    @MethodSource("invalidNumberValues")
    void updateNumberOfPatientsByIdMustThrowNullPointerException(Long id, Long numberOfPatients) {
        assertThrows(NullPointerException.class, () -> hospitalStaffService.updateNumberOfPatientsById(id, numberOfPatients));
    }

    private static Stream<Arguments> invalidNumberValues() {
        return Stream.of(
                Arguments.of(1L, null),
                Arguments.of(null, 5L),
                Arguments.of(null, null)
        );
    }
}