package com.epam.hospital.repositories;

import com.epam.hospital.data_access_layer.daos.ReadAndWriteDao;
import com.epam.hospital.data_access_layer.models.HospitalCard;
import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.data_access_layer.models.Patient;
import com.epam.hospital.data_access_layer.repositories.HospitalCardRepository;
import com.epam.hospital.data_access_layer.repositories.HospitalCardRepositoryImpl;
import com.epam.hospital.data_access_layer.repositories.HospitalStaffRepository;
import com.epam.hospital.data_access_layer.repositories.PatientRepository;
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
class HospitalCardRepositoryImplTest {

    private HospitalCardRepository hospitalCardRepository;

    @Mock
    private ReadAndWriteDao<HospitalCard> hospitalCardDao;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private HospitalStaffRepository hospitalStaffRepository;

    private HospitalCard hospitalCard;

    private List<HospitalCard> hospitalCards;

    private Patient patient;

    private HospitalStaff hospitalStaff;


    @BeforeEach
    void init() {
        hospitalCardRepository = new HospitalCardRepositoryImpl(hospitalCardDao, patientRepository, hospitalStaffRepository);
        hospitalCard = new HospitalCard();
        hospitalCard.setId(1L);
        hospitalCard.setDoctorId(1L);
        hospitalCard.setPatientId(1L);
        hospitalCard.setProcedures("procedures");
        hospitalCard.setMedications("medications");
        hospitalCard.setOperations("operations");
        hospitalCard.setDiagnosis("diagnosis");
        hospitalCards = new ArrayList<>();
        hospitalCards.add(hospitalCard);
        hospitalStaff = new HospitalStaff();
        hospitalStaff.setId(1L);
        patient = new Patient();
        patient.setId(1L);
        hospitalCard.setPatient(patient);
        hospitalCard.setDoctor(hospitalStaff);

    }

    @Test
    void addHospitalCardSuccess() {
        doNothing().when(hospitalCardDao).save(hospitalCard);
        hospitalCardRepository.addHospitalCard(hospitalCard);
        verify(hospitalCardDao, times(1)).save(hospitalCard);
    }

    @Test
    void getHospitalCardByIdSuccess() {
        when(hospitalCardDao.findById(hospitalCard.getId())).thenReturn(Optional.of(hospitalCard));
        when(patientRepository.getPatientById(hospitalCard.getPatientId())).thenReturn(Optional.of(patient));
        when(hospitalStaffRepository.getHospitalStaffById(hospitalCard.getDoctorId())).thenReturn(Optional.of(hospitalStaff));
        assertEquals(Optional.of(hospitalCard), hospitalCardRepository.getHospitalCardById(hospitalCard.getId()));
    }

    @Test
    void getHospitalCardByIdMustThrowIllegalArgumentException() {
        when(hospitalCardDao.findById(hospitalCard.getId())).thenReturn(Optional.of(hospitalCard));
        when(patientRepository.getPatientById(hospitalCard.getPatientId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalCardRepository.getHospitalCardById(hospitalCard.getId()));

        lenient().when(hospitalStaffRepository.getHospitalStaffById(hospitalCard.getDoctorId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalCardRepository.getHospitalCardById(hospitalCard.getId()));
    }

    @Test
    void getHospitalCardByIdMustReturnEmptyOptional() {
        when(hospitalCardDao.findById(hospitalCard.getId())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), hospitalCardRepository.getHospitalCardById(hospitalCard.getId()));
    }

    @Test
    void getAllHospitalCardsSuccess() {
        when(hospitalCardDao.findAll()).thenReturn(hospitalCards);
        when(patientRepository.getPatientById(hospitalCard.getPatientId())).thenReturn(Optional.of(patient));
        when(hospitalStaffRepository.getHospitalStaffById(hospitalCard.getDoctorId())).thenReturn(Optional.of(hospitalStaff));
        assertEquals(hospitalCards, hospitalCardRepository.getAllHospitalCards());
    }

    @Test
    void getAllHospitalCardsMustThrowIllegalArgumentException() {
        when(hospitalCardDao.findAll()).thenReturn(hospitalCards);
        when(patientRepository.getPatientById(hospitalCard.getPatientId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalCardRepository.getAllHospitalCards());

        lenient().when(hospitalStaffRepository.getHospitalStaffById(hospitalCard.getDoctorId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> hospitalCardRepository.getAllHospitalCards());
    }

    @Test
    void getAllPatientHospitalCardsByIdSuccess() {
        when(hospitalCardDao.findAll()).thenReturn(hospitalCards);
        when(patientRepository.getPatientById(hospitalCard.getPatientId())).thenReturn(Optional.of(patient));
        when(hospitalStaffRepository.getHospitalStaffById(hospitalCard.getDoctorId())).thenReturn(Optional.of(hospitalStaff));
        assertEquals(hospitalCards, hospitalCardRepository.getAllPatientHospitalCardsById(patient.getId()));
    }

    @Test
    void deleteHospitalCardByIdSuccess() {
        doNothing().when(hospitalCardDao).deleteById(hospitalCard.getId());
        hospitalCardRepository.deleteHospitalCardById(hospitalCard.getId());
        verify(hospitalCardDao, times(1)).deleteById(hospitalCard.getId());
    }

    @Test
    void updateProceduresByIdSuccess() {
        when(hospitalCardDao.findById(hospitalCard.getId())).thenReturn(Optional.of(hospitalCard));
        doNothing().when(hospitalCardDao).update(hospitalCard);
        hospitalCardRepository.updateProceduresById(hospitalCard.getId(), hospitalCard.getProcedures());
        verify(hospitalCardDao, times(1)).update(hospitalCard);
    }

    @Test
    void updateMedicationsByIdSuccess() {
        when(hospitalCardDao.findById(hospitalCard.getId())).thenReturn(Optional.of(hospitalCard));
        doNothing().when(hospitalCardDao).update(hospitalCard);
        hospitalCardRepository.updateMedicationsById(hospitalCard.getId(), hospitalCard.getMedications());
        verify(hospitalCardDao, times(1)).update(hospitalCard);
    }

    @Test
    void updateOperationsById() {
        when(hospitalCardDao.findById(hospitalCard.getId())).thenReturn(Optional.of(hospitalCard));
        doNothing().when(hospitalCardDao).update(hospitalCard);
        hospitalCardRepository.updateOperationsById(hospitalCard.getId(), hospitalCard.getOperations());
        verify(hospitalCardDao, times(1)).update(hospitalCard);
    }

    @Test
    void updateDiagnosisById() {
        when(hospitalCardDao.findById(hospitalCard.getId())).thenReturn(Optional.of(hospitalCard));
        doNothing().when(hospitalCardDao).update(hospitalCard);
        hospitalCardRepository.updateDiagnosisById(hospitalCard.getId(), hospitalCard.getDiagnosis());
        verify(hospitalCardDao, times(1)).update(hospitalCard);
    }
}