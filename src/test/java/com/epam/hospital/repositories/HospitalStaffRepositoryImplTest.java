package com.epam.hospital.repositories;

import com.epam.hospital.data_access_layer.daos.HospitalStaffDao;
import com.epam.hospital.data_access_layer.daos.ReadOnlyDao;
import com.epam.hospital.data_access_layer.models.Category;
import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.data_access_layer.models.Role;
import com.epam.hospital.data_access_layer.repositories.HospitalStaffRepository;
import com.epam.hospital.data_access_layer.repositories.HospitalStaffRepositoryImpl;
import com.epam.hospital.utils.Sorting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospitalStaffRepositoryImplTest {

    private HospitalStaffRepository hospitalStaffRepository;

    @Mock
    private HospitalStaffDao hospitalStaffDao;

    @Mock
    private ReadOnlyDao<Role> roleDao;

    @Mock
    private ReadOnlyDao<Category> categoryDao;

    private HospitalStaff hospitalStaff;

    private List<HospitalStaff> hospitalStaffList;

    private Role role;

    private Category category;

    @BeforeEach
    void init() {
        hospitalStaffRepository = new HospitalStaffRepositoryImpl(hospitalStaffDao, roleDao, categoryDao);
        role = new Role(1L, "DOCTOR");
        category = new Category(1L, "Oncologist");
        hospitalStaff = new HospitalStaff();
        hospitalStaff.setId(1L);
        hospitalStaff.setUsername("Username1");
        hospitalStaff.setPassword("TestPassword@123");
        hospitalStaff.setFirstName("John");
        hospitalStaff.setLastName("Dow");
        hospitalStaff.setRole(role);
        hospitalStaff.setCategory(category);
        hospitalStaffList = new ArrayList<>();
        hospitalStaffList.add(hospitalStaff);
    }

    @Test
    void addStaffSuccess() {
        when(roleDao.findByName(role.getName())).thenReturn(Optional.of(role));
        when(categoryDao.findByName(category.getName())).thenReturn(Optional.of(category));
        doNothing().when(hospitalStaffDao).save(hospitalStaff);
        hospitalStaffRepository.addStaff(hospitalStaff);
        verify(hospitalStaffDao, times(1)).save(hospitalStaff);
    }

    @Test
    void addStaffMustThrowIllegalArgumentException() {
        when(roleDao.findByName(role.getName())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.addStaff(hospitalStaff));

        lenient().when(categoryDao.findByName(category.getName())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.addStaff(hospitalStaff));
    }

    @Test
    void getHospitalStaffByIdSuccess() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        when(roleDao.findById(role.getId())).thenReturn(Optional.of(role));
        when(categoryDao.findById(category.getId())).thenReturn(Optional.of(category));
        assertEquals(Optional.of(hospitalStaff), hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId()));
    }

    @Test
    void getHospitalStaffByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        when(roleDao.findById(role.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId()));

        lenient().when(categoryDao.findById(category.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId()));
    }

    @Test
    void getHospitalStaffByIdMustReturnEmptyOptional() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), hospitalStaffRepository.getHospitalStaffById(hospitalStaff.getId()));
    }

    @Test
    void getHospitalStaffByUsernameSuccess() {
        when(hospitalStaffDao.findByUsername(hospitalStaff.getUsername())).thenReturn(Optional.of(hospitalStaff));
        when(roleDao.findById(role.getId())).thenReturn(Optional.of(role));
        when(categoryDao.findById(category.getId())).thenReturn(Optional.of(category));
        assertEquals(Optional.of(hospitalStaff), hospitalStaffRepository.getHospitalStaffByUsername(hospitalStaff.getUsername()));
    }

    @Test
    void getHospitalStaffByUsernameMustThrowIllegalArgumentException() {
        when(hospitalStaffDao.findByUsername(hospitalStaff.getUsername())).thenReturn(Optional.of(hospitalStaff));
        when(roleDao.findById(role.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getHospitalStaffByUsername(hospitalStaff.getUsername()));

        lenient().when(categoryDao.findById(category.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getHospitalStaffByUsername(hospitalStaff.getUsername()));
    }

    @Test
    void getHospitalStaffByUsernameMustReturnEmptyOptional() {
        when(hospitalStaffDao.findByUsername(hospitalStaff.getUsername())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), hospitalStaffRepository.getHospitalStaffByUsername(hospitalStaff.getUsername()));
    }

    @Test
    void getAllHospitalStaffSuccess() {
        when(hospitalStaffDao.findAll()).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.of(role));
        when(categoryDao.findById(category.getId())).thenReturn(Optional.of(category));
        assertEquals(hospitalStaffList, hospitalStaffRepository.getAllHospitalStaff());
    }

    @Test
    void getAllHospitalStaffMustThrowIllegalArgumentException() {
        when(hospitalStaffDao.findAll()).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaff());

        lenient().when(categoryDao.findById(category.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaff());
    }

    @Test
    void getAllHospitalStaffPageableSuccess() {
        when(hospitalStaffDao.findAllPageable(4, 10)).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.of(role));
        when(categoryDao.findById(category.getId())).thenReturn(Optional.of(category));
        assertEquals(hospitalStaffList, hospitalStaffRepository.getAllHospitalStaffPageable(4, 10));
    }

    @Test
    void getAllHospitalStaffPageableMustThrowIllegalArgumentException() {
        when(hospitalStaffDao.findAllPageable(4, 10)).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaffPageable(4, 10));

        lenient().when(categoryDao.findById(category.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaffPageable(4, 10));
    }

    @Test
    void getAllHospitalStaffPageableAndSortedAlphabeticallySuccess() {
        when(hospitalStaffDao.findAllPageableAndSorted(4, 10, Sorting.ALPHABETICALLY)).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.of(role));
        when(categoryDao.findById(category.getId())).thenReturn(Optional.of(category));
        assertEquals(hospitalStaffList, hospitalStaffRepository.getAllHospitalStaffPageableAndSortedAlphabetically(4, 10));
    }

    @Test
    void getAllHospitalStaffPageableAndSortedAlphabeticallyMustThrowIllegalArgumentException() {
        when(hospitalStaffDao.findAllPageableAndSorted(4, 10, Sorting.ALPHABETICALLY)).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaffPageableAndSortedAlphabetically(4, 10));

        lenient().when(categoryDao.findById(category.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaffPageableAndSortedAlphabetically(4, 10));
    }

    @Test
    void getAllHospitalStaffPageableAndSortedByCategorySuccess() {
        when(hospitalStaffDao.findAllPageableAndSortedByCategory(4, 10)).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.of(role));
        when(categoryDao.findById(category.getId())).thenReturn(Optional.of(category));
        assertEquals(hospitalStaffList, hospitalStaffRepository.getAllHospitalStaffPageableAndSortedByCategory(4, 10));
    }

    @Test
    void getAllHospitalStaffPageableAndSortedByCategoryMustThrowIllegalArgumentException() {
        when(hospitalStaffDao.findAllPageableAndSortedByCategory(4, 10)).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaffPageableAndSortedByCategory(4, 10));

        lenient().when(categoryDao.findById(category.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaffPageableAndSortedByCategory(4, 10));
    }

    @Test
    void getAllHospitalStaffPageableAndSortedByNumberOfPatientsSuccess() {
        when(hospitalStaffDao.findAllPageableAndSorted(4, 10, Sorting.NUMBER_OF_PATIENTS)).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.of(role));
        when(categoryDao.findById(category.getId())).thenReturn(Optional.of(category));
        assertEquals(hospitalStaffList, hospitalStaffRepository.getAllHospitalStaffPageableAndSortedByNumberOfPatients(4, 10));
    }

    @Test
    void getAllHospitalStaffPageableAndSortedByNumberOfPatientsMustThrowIllegalArgumentException() {
        when(hospitalStaffDao.findAllPageableAndSorted(4, 10, Sorting.NUMBER_OF_PATIENTS)).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaffPageableAndSortedByNumberOfPatients(4, 10));

        lenient().when(categoryDao.findById(category.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaffPageableAndSortedByNumberOfPatients(4, 10));
    }

    @Test
    void getNumberOfRowsSuccess() {
        when(hospitalStaffDao.getNumberOfRows()).thenReturn(10);
        assertEquals(10, hospitalStaffRepository.getNumberOfRows());
    }

    @Test
    void getAllHospitalStaffByPatientIdSuccess() {
        when(hospitalStaffDao.findAllByPatientId(1L)).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.of(role));
        when(categoryDao.findById(category.getId())).thenReturn(Optional.of(category));
        assertEquals(hospitalStaffList, hospitalStaffRepository.getAllHospitalStaffByPatientId(1L));
    }

    @Test
    void getAllHospitalStaffByPatientIdMustThrowIllegalArgumentException() {
        when(hospitalStaffDao.findAllByPatientId(1L)).thenReturn(hospitalStaffList);
        when(roleDao.findById(role.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaffByPatientId(1L));

        lenient().when(categoryDao.findById(category.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalStaffRepository.getAllHospitalStaffByPatientId(1L));
    }

    @Test
    void deleteStaffByIdSuccess() {
        doNothing().when(hospitalStaffDao).deleteById(hospitalStaff.getId());
        hospitalStaffRepository.deleteStaffById(hospitalStaff.getId());
        verify(hospitalStaffDao, times(1)).deleteById(hospitalStaff.getId());
    }

    @Test
    void updateStaffUsernameByIdSuccess() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffDao).update(hospitalStaff);
        hospitalStaffRepository.updateStaffUsernameById(hospitalStaff.getId(), hospitalStaff.getUsername());
        verify(hospitalStaffDao, times(1)).update(hospitalStaff);
    }

    @Test
    void updateStaffPasswordByIdSuccess() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffDao).update(hospitalStaff);
        hospitalStaffRepository.updateStaffPasswordById(hospitalStaff.getId(), hospitalStaff.getPassword());
        verify(hospitalStaffDao, times(1)).update(hospitalStaff);
    }

    @Test
    void updateStaffRoleByIdSuccess() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        when(roleDao.findByName(role.getName())).thenReturn(Optional.of(role));
        doNothing().when(hospitalStaffDao).update(hospitalStaff);
        hospitalStaffRepository.updateStaffRoleById(hospitalStaff.getId(), role.getName());
        verify(hospitalStaffDao, times(1)).update(hospitalStaff);
    }

    @Test
    void updateStaffRoleByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        when(roleDao.findByName(role.getName())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,  () -> hospitalStaffRepository.updateStaffRoleById(hospitalStaff.getId(), role.getName()));
    }

    @Test
    void updateStaffCategoryByIdSuccess() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        when(categoryDao.findByName(category.getName())).thenReturn(Optional.of(category));
        doNothing().when(hospitalStaffDao).update(hospitalStaff);
        hospitalStaffRepository.updateStaffCategoryById(hospitalStaff.getId(), category.getName());
        verify(hospitalStaffDao, times(1)).update(hospitalStaff);
    }

    @Test
    void updateStaffCategoryByIdMustThrowIllegalArgumentException() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        when(categoryDao.findByName(category.getName())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,  () -> hospitalStaffRepository.updateStaffCategoryById(hospitalStaff.getId(), category.getName()));
    }

    @Test
    void updateStaffFirstNameByIdSuccess() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffDao).update(hospitalStaff);
        hospitalStaffRepository.updateStaffFirstNameById(hospitalStaff.getId(), hospitalStaff.getFirstName());
        verify(hospitalStaffDao, times(1)).update(hospitalStaff);
    }

    @Test
    void updateStaffLastNameByIdSuccess() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffDao).update(hospitalStaff);
        hospitalStaffRepository.updateStaffLastNameById(hospitalStaff.getId(), hospitalStaff.getLastName());
        verify(hospitalStaffDao, times(1)).update(hospitalStaff);
    }

    @Test
    void updateStaffNumberOfPatientsByIdSuccess() {
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(hospitalStaffDao).update(hospitalStaff);
        hospitalStaffRepository.updateStaffNumberOfPatientsById(hospitalStaff.getId(), 10);
        verify(hospitalStaffDao, times(1)).update(hospitalStaff);
    }
}