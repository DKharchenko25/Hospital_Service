package com.epam.hospital.repositories;

import com.epam.hospital.data_access_layer.daos.ReadAndWriteDao;
import com.epam.hospital.data_access_layer.daos.PatientDao;
import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.data_access_layer.models.Patient;
import com.epam.hospital.data_access_layer.repositories.PatientRepository;
import com.epam.hospital.data_access_layer.repositories.PatientRepositoryImpl;
import com.epam.hospital.utils.Sorting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientRepositoryImplTest {

    private PatientRepository patientRepository;

    @Mock
    private PatientDao patientDao;

    @Mock
    private ReadAndWriteDao<HospitalStaff> hospitalStaffDao;

    private Patient patient;

    private List<Patient> patients;

    private HospitalStaff hospitalStaff;


    @BeforeEach
    void init() {
        patientRepository = new PatientRepositoryImpl(patientDao, hospitalStaffDao);
        patient = new Patient();
        patient.setId(1L);
        patient.setUsername("Username1");
        patient.setPassword("TestPassword@123");
        patient.setFirstName("John");
        patient.setLastName("Dow");
        patient.setBirthDate("1996-02-26");
        patient.setEmail("example@mail.com");
        patient.setPhoneNumber("+380973421234");
        patients = new ArrayList<>();
        patients.add(patient);
        hospitalStaff = new HospitalStaff();
        hospitalStaff.setId(1L);
    }


    @Test
    void addPatientSuccess() {
        doNothing().when(patientDao).save(patient);
        patientRepository.addPatient(patient);
        verify(patientDao, times(1)).save(patient);
    }

    @Test
    void getPatientByIdSuccess() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.of(patient));
        assertEquals(Optional.of(patient), patientRepository.getPatientById(patient.getId()));
    }

    @Test
    void getPatientByIdMustReturnEmptyOptional() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), patientRepository.getPatientById(patient.getId()));
    }

    @Test
    void getPatientByUsernameSuccess() {
        when(patientDao.findByUsername(patient.getUsername())).thenReturn(Optional.of(patient));
        assertEquals(Optional.of(patient), patientRepository.getPatientByUsername(patient.getUsername()));
    }


    @Test
    void getPatientByUsernameMustReturnEmptyOptional() {
        when(patientDao.findByUsername(patient.getUsername())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), patientRepository.getPatientByUsername(patient.getUsername()));
    }

    @Test
    void getAllPatientsSuccess() {
        when(patientDao.findAll()).thenReturn(patients);
        assertEquals(patients, patientRepository.getAllPatients());
    }


    @Test
    void getAllPatientsPageableSuccess() {
        when(patientDao.findAllPageable(4, 10)).thenReturn(patients);
        assertEquals(patients, patientRepository.getAllPatientsPageable(4, 10));
    }

    @Test
    void getAllPatientsPageableAndSortedAlphabeticallySuccess() {
        when(patientDao.findAllPageableAndSorted(4, 10, Sorting.ALPHABETICALLY)).thenReturn(patients);
        assertEquals(patients, patientRepository.getAllPatientsPageableAndSortedAlphabetically(4, 10));
    }

    @Test
    void getAllPatientsPageableAndSortedByBirthDateSuccess() {
        when(patientDao.findAllPageableAndSorted(4, 10, Sorting.BIRTH_DATE)).thenReturn(patients);
        assertEquals(patients, patientRepository.getAllPatientsPageableAndSortedByBirthDate(4, 10));
    }


    @Test
    void getNumberOfRowsSuccess() {
        when(patientDao.getNumberOfRows()).thenReturn(10);
        assertEquals(10, patientRepository.getNumberOfRows());
    }

    @Test
    void getAllPatientsByHospitalStaffIdSuccess() {
        when(patientDao.findAllByHospitalStaffId(1L)).thenReturn(patients);
        assertEquals(patients, patientRepository.getAllPatientsByHospitalStaffId(1L));
    }


    @Test
    void appointPatientToDoctorSuccess() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(patientDao).appointPatientToDoctor(patient.getId(), hospitalStaff.getId());
        patientRepository.appointPatientToDoctor(patient.getId(), hospitalStaff.getId());
        verify(patientDao, times(1)).appointPatientToDoctor(patient.getId(), hospitalStaff.getId());
    }

    @Test
    void appointPatientToDoctorMustThrowIllegalArgumentException() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientRepository.appointPatientToDoctor(patient.getId(), hospitalStaff.getId()));

        lenient().when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientRepository.appointPatientToDoctor(patient.getId(), hospitalStaff.getId()));
    }

    @Test
    void dischargePatientFromDoctorSuccess() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.of(hospitalStaff));
        doNothing().when(patientDao).dischargePatientFromDoctor(patient.getId(), hospitalStaff.getId());
        patientRepository.dischargePatientFromDoctor(patient.getId(), hospitalStaff.getId());
        verify(patientDao, times(1)).dischargePatientFromDoctor(patient.getId(), hospitalStaff.getId());
    }

    @Test
    void dischargePatientFromDoctorMustThrowIllegalArgumentException() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientRepository.dischargePatientFromDoctor(patient.getId(), hospitalStaff.getId()));

        lenient().when(hospitalStaffDao.findById(hospitalStaff.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientRepository.dischargePatientFromDoctor(patient.getId(), hospitalStaff.getId()));
    }

    @Test
    void deletePatientByIdSuccess() {
        doNothing().when(patientDao).deleteById(patient.getId());
        patientRepository.deletePatientById(patient.getId());
        verify(patientDao, times(1)).deleteById(patient.getId());
    }

    @Test
    void updatePatientUsernameByIdSuccess() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.of(patient));
        patientRepository.updatePatientUsernameById(patient.getId(), patient.getUsername());
        verify(patientDao, times(1)).update(patient);
    }

    @Test
    void updatePatientPasswordByIdSuccess() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.of(patient));
        patientRepository.updatePatientPasswordById(patient.getId(), patient.getPassword());
        verify(patientDao, times(1)).update(patient);
    }

    @Test
    void updatePatientFirstNameByIdSuccess() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.of(patient));
        patientRepository.updatePatientFirstNameById(patient.getId(), patient.getFirstName());
        verify(patientDao, times(1)).update(patient);
    }

    @Test
    void updatePatientLastNameByIdSuccess() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.of(patient));
        patientRepository.updatePatientLastNameById(patient.getId(), patient.getLastName());
        verify(patientDao, times(1)).update(patient);
    }

    @Test
    void updatePatientBirthDateByIdSuccess() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.of(patient));
        patientRepository.updatePatientBirthDateById(patient.getId(), patient.getBirthDate());
        verify(patientDao, times(1)).update(patient);
    }

    @Test
    void updatePatientEmailByIdSuccess() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.of(patient));
        patientRepository.updatePatientEmailById(patient.getId(), patient.getEmail());
        verify(patientDao, times(1)).update(patient);
    }

    @Test
    void updatePatientPhoneNumberByIdSuccess() {
        when(patientDao.findById(patient.getId())).thenReturn(Optional.of(patient));
        patientRepository.updatePatientPhoneNumberById(patient.getId(), patient.getPhoneNumber());
        verify(patientDao, times(1)).update(patient);
    }

}